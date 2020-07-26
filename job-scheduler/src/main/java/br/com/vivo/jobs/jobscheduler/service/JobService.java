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

@Service
public class JobService {

	public List<List<Long>> createScheduleList(List<Job> jobList, JanelaExecucao janelaExecucao) {

		List<List<Long>> scheduleList = new ArrayList<List<Long>>();
		Job jobSelecionado;
		List<Job> jobPendentes = new ArrayList<Job>();

		Long totalJobsGroup = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(),
				ChronoUnit.HOURS) / 8;
		Long horasRestantes = janelaExecucao.getDataHoraInicio().until(janelaExecucao.getDataHoraFim(),
				ChronoUnit.HOURS) % 8;

		List<JobGroup> listGroup = createJobGroups(janelaExecucao.getDataHoraInicio(), totalJobsGroup, horasRestantes);

		while (!jobList.isEmpty()) {

			jobSelecionado = getJobDataMaxMenor(jobList);
			if (!putJobInGroup(jobSelecionado, listGroup, 0)) {
				jobPendentes.add(jobSelecionado);
			}
			removeJobList(jobList, jobSelecionado);
		}

		return preencheScheduleList(scheduleList, listGroup);

	}

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

	private Job getJobDataMaxMenor(List<Job> jobList) {
		Job job = jobList.get(0);

		for (int i = 1; i < jobList.size(); i++) {
			if (job.getDataMaximaConclusao().isAfter(jobList.get(i).getDataMaximaConclusao())) {
				job = jobList.get(i);
			}
		}

		return job;
	}

	private void removeJobList(List<Job> jobList, Job job) {
		jobList.remove(job);
	}

	private boolean putJobInGroup(Job job, List<JobGroup> listGroup, int nivel) {

		JobGroup jobGroup = listGroup.get(nivel);

		if (jobGroup.getTempoOcioso().compareTo(job.getTempoEstimado()) == 1
				|| jobGroup.getTempoOcioso().compareTo(job.getTempoEstimado()) == 0) {
			jobGroup.getJobList().add(job);
			jobGroup.setTempoOcioso(jobGroup.getTempoOcioso().subtract(job.getTempoEstimado()));
			return true;
		} else {
			return remanejaJob(job, listGroup, nivel);
		}

	}

	private boolean remanejaJob(Job job, List<JobGroup> listGroup, int nivel) {

		BigDecimal tempoOciosoRestante;

		for (int i = nivel; i < listGroup.size(); i++) {
			JobGroup jobGroup = listGroup.get(i);

			tempoOciosoRestante = job.getTempoEstimado().subtract(jobGroup.getTempoOcioso());

			if (tempoOciosoRestante.doubleValue() >= 0) {

				List<Job> groupJobList = jobGroup.getJobList();

				for (int a = 0; a < groupJobList.size(); a++) {

					Job jobEmAnalise = groupJobList.get(a);

					if ((jobEmAnalise.getTempoEstimado().compareTo(tempoOciosoRestante) == 1
							|| jobEmAnalise.getTempoEstimado().compareTo(tempoOciosoRestante) == 0)
							&& jobEmAnalise.getTempoEstimado().compareTo(job.getTempoEstimado()) == -1) {

						JobGroup jobGroupProx = listGroup.get(i + 1);

						if ((jobGroupProx.getFim().isBefore(jobEmAnalise.getDataMaximaConclusao())
								|| jobGroupProx.getFim().isEqual(jobEmAnalise.getDataMaximaConclusao()))
								&& (jobGroupProx.getTempoOcioso().compareTo(jobEmAnalise.getTempoEstimado()) == 1
										|| jobGroupProx.getTempoOcioso()
												.compareTo(jobEmAnalise.getTempoEstimado()) == 0)) {
							
							removeJobList(groupJobList, jobEmAnalise);
							jobGroup.setTempoOcioso(jobGroup.getTempoOcioso()
									.add(jobEmAnalise.getTempoEstimado()));
							jobGroup.getJobList().add(job);
							jobGroup.setTempoOcioso(jobGroup.getTempoOcioso().subtract(job.getTempoEstimado()));

							return putJobInGroup(jobEmAnalise, listGroup, nivel + 1);
						}
					}
				}
			}

		}

		return putJobInGroup(job, listGroup, nivel + 1);

	}

	private List<List<Long>> preencheScheduleList(List<List<Long>> scheduleList, List<JobGroup> listGroup) {
		List<Long> jobsGroupId;

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
