package com.vm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class Visita implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visita_sequence")
	@SequenceGenerator(name = "visita_sequence", sequenceName = "visita_seq")
	private Long idvisita;

	private String observacao;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_agendamento;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	@Column(length = 20)
	private String horario;

	@Column(length = 20)
	private String horario_fim;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private StatusVisita statusvisita;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "prescritor_fk")
	private Prescritor prescritor;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "visitador_fk")
	private Visitador visitador;

	@OneToMany(mappedBy = "visita", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<AtivoVisita> ativo_visita;

	public List<AtivoVisita> getAtivo_visita() {
		return ativo_visita;
	}

	public void setAtivo_visita(List<AtivoVisita> ativo_visita) {
		this.ativo_visita = ativo_visita;
	}

	public Long getIdvisita() {
		return idvisita;
	}

	public void setIdvisita(Long idvisita) {
		this.idvisita = idvisita;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getData_agendamento() {
		return data_agendamento;
	}

	public void setData_agendamento(Date data_agendamento) {
		this.data_agendamento = data_agendamento;
	}

	public StatusVisita getStatusvisita() {
		return statusvisita;
	}

	public void setStatusvisita(StatusVisita statusvisita) {
		this.statusvisita = statusvisita;
	}

	public Prescritor getPrescritor() {
		return prescritor;
	}

	public void setPrescritor(Prescritor prescritor) {
		this.prescritor = prescritor;
	}

	public Visitador getVisitador() {
		return visitador;
	}

	public void setVisitador(Visitador visitador) {
		this.visitador = visitador;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void setHorario_fim(String horario_fim) {
		this.horario_fim = horario_fim;
	}

	public String getHorario_fim() {
		return horario_fim;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_alteracao(Date data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public Date getData_alteracao() {
		return data_alteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idvisita == null) ? 0 : idvisita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visita other = (Visita) obj;
		if (idvisita == null) {
			if (other.idvisita != null)
				return false;
		} else if (!idvisita.equals(other.idvisita))
			return false;
		return true;
	}

}
