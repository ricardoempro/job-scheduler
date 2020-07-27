package br.com.vivo.jobs.jobscheduler.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vivo.jobs.jobscheduler.model.JanelaExecucao;
import br.com.vivo.jobs.jobscheduler.model.Job;
import br.com.vivo.jobs.jobscheduler.model.JobGroup;

/**
* Classe da camada de Services que contém os metódos para criar o plano de execução dos Jobs.
* 
* @author Ricardo Neves
* 
*/

@Service
public class JobService {

	/**
	 * Método utilizado para organizar os jobs dentro dos grupos de 8h que são executados na janela de execução.
	 * Os jobs são organizados de maneira o otimizar a execução dentro das 8 horas de um grupo respeitando a data máxima para conclusão.
	 * 
	 * Os jobs da lista que não couberem na janela de execução, ficam armazenados em uma lista, mas por hora são desconsiderados.
	 * Mas possível implementação para retorna-los.
	 *  
	 * @see {@link br.com.vivo.jobs.jobscheduler.model.JanelaExecucao}
	 * @param jobList uma lista de jobs a serem organizadas
	 * @param janelaExecucao janela de execução que contém o período que os jobs devem ser executados.
	 * @return Array de Arrays contendo os ids dos jobs nos grupos em ordem de execução. 
	 */	
	public List<List<Long>> createScheduleList(List<Job> jobList, JanelaExecucao janelaExecucao) {

		List<Job> jobPendentes = new ArrayList<Job>();
		Job jobSelecionado;

		Long totalJobsGroup = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(),
				ChronoUnit.HOURS) / 8;
		Long horasRestantes = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(),
				ChronoUnit.HOURS) % 8;

		List<JobGroup> listGroup = createJobGroups(janelaExecucao.getDataHoraInicio(), totalJobsGroup, horasRestantes);

		while (!jobList.isEmpty()) {

			jobSelecionado = getJobDateMaxMinor(jobList);
			if (putJobInGroup(jobSelecionado, listGroup, 0)) {
				jobList.remove(jobSelecionado);
			} else {
				jobPendentes.add(jobSelecionado);
				jobList.remove(jobSelecionado);
			}
		}

		return fillScheduleList(listGroup);

	}

	/**
	* Método que cria os grupos de 8h representados em um objeto JobGroup.
	* Caso o período tenha horas restantes que não forme um período completo de 8 horas,
	*  um grupo diferenciado é criado para verificar se algum job ainda pode ser executado. 
	* @see {@link br.com.vivo.jobs.jobscheduler.model.JobGroup}
	* @param inicio data do inicio da janela
	* @param totalJobsGroup total de grupos de 8 horas
	* @param horasRestantes horas restantes que não formam um grupo de 8 horas
	* @return lista de grupos.
	*/
	private List<JobGroup> createJobGroups(LocalDateTime inicio, Long totalJobsGroup, Long horasRestantes) {

		List<JobGroup> listGroup = new ArrayList<JobGroup>();

		for (int i = 1; i <= totalJobsGroup; i++) {
			listGroup.add(new JobGroup(inicio.plusHours(8 * (i - 1)), inicio.plusHours(8 * i), 8));
		}

		listGroup.add(new JobGroup(inicio.plusHours(8 * totalJobsGroup),
				inicio.plusHours(8 * totalJobsGroup).plusHours(horasRestantes),
				Integer.valueOf(horasRestantes.toString())));

		return listGroup;

	}

	/**
	* Método encontra o job com a menor data máxima de conclusão de uma lista de jobs
	* @see {@link br.com.vivo.jobs.jobscheduler.model.Job}
	* @param jobList lista de jobs em processamento
	* @return Job encontrado
	*/
	private Job getJobDateMaxMinor(List<Job> jobList) {
		Job job = jobList.get(0);

		for (int i = 1; i < jobList.size(); i++) {
			if (job.getDataMaximaConclusao().isAfter(jobList.get(i).getDataMaximaConclusao())) {
				job = jobList.get(i);
			}
		}

		return job;
	}

	/**
	* Método que adiciona um job em um dos grupos criados
	* @see {@link br.com.vivo.jobs.jobscheduler.model.Job}
	* 	* @see {@link br.com.vivo.jobs.jobscheduler.model.JobGroup}
	* @param job que será inserido em um grupo (na recursão sempre os prioritários são executados primeiro)
	* @param listGroup lista de grupos de períodos disponível 
	* @param nivel indica o indice da recursão para remanejar jobs quando for conveniente uma otimização na execução pelo método {@link br.com.vivo.jobs.jobscheduler.service.JobService#rearrangeJob(Job, List, int)}. 
	* @return true se foi inserido em um grupo e false se não.
	*/
	private boolean putJobInGroup(Job job, List<JobGroup> listGroup, int nivel) {

		if (nivel < listGroup.size()) {
			JobGroup jobGroup = listGroup.get(nivel);

			if (jobGroup.getTempoOcioso().compareTo(job.getTempoEstimado()) == 1
					|| jobGroup.getTempoOcioso().compareTo(job.getTempoEstimado()) == 0) {
				jobGroup.getJobList().add(job);
				jobGroup.setTempoOcioso(jobGroup.getTempoOcioso().subtract(job.getTempoEstimado()));
				return true;
			} else {
				return rearrangeJob(job, listGroup, nivel);
			}
		} else {
			return false;
		}

	}

	/**
	* Método que remaneja um job entre os grupos criados
	* 
	* Para um job ser remanejado é realizado a seguinte verificação:
	* 	1º - Calcula-se o tempo que resta: tempo estimado do job menos o restante disponível no grupo
	*  		(Sabemos que é maior pois ele não foi incluído no método {@link br.com.vivo.jobs.jobscheduler.service.JobService#putJobInGroup(Job, List, int)} 
	* 
	* 	2º - Para cada job que já esta dentro do grupo, verificamos um que o tempo estimado seja maior ou igual ao tempo que resta do job,
	* 		mas que tenha o tempo estimado menor que a do job remanejado (Só assim valerá a pena posterga-lo em termos de horas uteis).
	* 
	* 	3º - Caso 2º passo for contemplado, verifica-se no próximo a data máxima de conclusão do job e se o tempo disponivel no grupo permite executar o job no tempo estimado.
	* 
	* 	4º - Se o 3º passo for contemplado os job é remanejado, caso contrario eu sigo para o próximo grupo com o job em analise.
	* 
	* @param job que será inserido em um grupo
	* @param listGroup lista de grupos de períodos disponível 
	* @param nivel indica o indice da recursão para que ao repassar job remanejado avance ao grupo seguinte}. 
	* @return repassa o retono do metodo {@link br.com.vivo.jobs.jobscheduler.service.JobService#putJobInGroup(Job, List, int) para a camada superior da recursão.
	*/
	private boolean rearrangeJob(Job job, List<JobGroup> listGroup, int nivel) {

		BigDecimal tempoRestaJob;

		for (int i = nivel; i < listGroup.size(); i++) {
			JobGroup jobGroup = listGroup.get(i);

			tempoRestaJob = job.getTempoEstimado().subtract(jobGroup.getTempoOcioso());

			if (tempoRestaJob.doubleValue() >= 0) {

				List<Job> groupJobList = jobGroup.getJobList();

				for (int a = 0; a < groupJobList.size(); a++) {

					Job jobEmAnalise = groupJobList.get(a);

					if ((jobEmAnalise.getTempoEstimado().compareTo(tempoRestaJob) == 1
							|| jobEmAnalise.getTempoEstimado().compareTo(tempoRestaJob) == 0)
							&& jobEmAnalise.getTempoEstimado().compareTo(job.getTempoEstimado()) == -1) {

						JobGroup jobGroupProx = listGroup.get(i + 1); 

						if ((jobGroupProx.getFim().isBefore(jobEmAnalise.getDataMaximaConclusao())
								|| jobGroupProx.getFim().isEqual(jobEmAnalise.getDataMaximaConclusao()))
								&& (jobGroupProx.getTempoOcioso().compareTo(jobEmAnalise.getTempoEstimado()) == 1
										|| jobGroupProx.getTempoOcioso()
												.compareTo(jobEmAnalise.getTempoEstimado()) == 0)) {
							
							groupJobList.remove(jobEmAnalise);
							jobGroup.setTempoOcioso(jobGroup.getTempoOcioso()
									.add(jobEmAnalise.getTempoEstimado()));
							groupJobList.add(job);
							jobGroup.setTempoOcioso(jobGroup.getTempoOcioso().subtract(job.getTempoEstimado()));

							return putJobInGroup(jobEmAnalise, listGroup, nivel + 1);
						}	
					}
				}
			}

		}

		return putJobInGroup(job, listGroup, nivel + 1);

	}

	/**
	* Método cria a lista de listas de ids dos jobs organizados nos grupos de 8h em ordem de execução.
	* 
	* @param listGroup lista de grupos de períodos disponível com os jobs que serão executados.
	* @return lista dos grupos de jobs somente com id
	*/
	private List<List<Long>> fillScheduleList(List<JobGroup> listGroup) {
		List<Long> jobsGroupId;
		List<List<Long>> scheduleList = new ArrayList<List<Long>>();

		for (JobGroup jobGroup : listGroup) {

			jobsGroupId = new ArrayList<Long>();

			for (Job job : jobGroup.getJobList()) {
				jobsGroupId.add(job.getId());
			}

			if (!jobsGroupId.isEmpty()) {
				scheduleList.add(jobsGroupId);
			}
		}

		return scheduleList;

	}

}
