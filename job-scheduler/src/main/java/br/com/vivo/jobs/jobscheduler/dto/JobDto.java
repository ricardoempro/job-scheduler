package br.com.vivo.jobs.jobscheduler.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.vivo.jobs.jobsscheduler.valid.TempoEstimadoValid;

public class JobDto {

	@JsonProperty("ID")
	@NotNull(message = "O ID deve ser informado!")
	private Long id;

	@JsonProperty("Descrição")
	@NotEmpty(message = "A descrição não pode ser vazia ou nula!")
	@Size(min = 3)
	private String descricao;

	@JsonProperty("Data Máxima de conclusão")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "A data máxima de conclusão deve ser informada!")
	private LocalDateTime dataMaximaConclusao;

	@JsonProperty("Tempo estimado")
	@TempoEstimadoValid(message = "Deve ser informado no seguinte formato:" + (char) 34
			+ " {quantidade de horas} hora(s) {quantidade de minutos} min" + (char) 34
			+ " Exemplos: 1 hora 30 min / 2 horas 45 min / 1 hora / 50 min")
	private String tempoEstimadoStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataMaximaConclusao() {
		return dataMaximaConclusao;
	}

	public void setDataMaximaConclusao(LocalDateTime dataMaximaConclusao) {
		this.dataMaximaConclusao = dataMaximaConclusao;
	}

	public String getTempoEstimadoStr() {
		return tempoEstimadoStr;
	}

	public void setTempoEstimadoStr(String tempoExtimadoStr) {
		this.tempoEstimadoStr = tempoExtimadoStr;
	}

}
