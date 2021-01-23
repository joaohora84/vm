package com.vm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@SuppressWarnings("deprecation")
@Entity
public class Prescricao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescricao_sequence")
	@SequenceGenerator(name = "prescricao_sequence", sequenceName = "prescricao_seq")
	private Long idprescricao;

	@ManyToOne
	@ForeignKey(name = "prescritor_fk")
	private Prescritor prescritor;

	@Column(length = 100)
	private String nome_paciente;

	@Column(length = 150)
	private String endereco_paciente;

	@Column(length = 20)
	private String telefone_paciente;

	@OneToMany(mappedBy = "prescricao", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<FormulaPrescricao> formula_prescricao;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	public void setFormula_prescricao(List<FormulaPrescricao> formula_prescricao) {
		this.formula_prescricao = formula_prescricao;
	}

	public List<FormulaPrescricao> getFormula_prescricao() {
		return formula_prescricao;
	}

	public Long getIdprescricao() {
		return idprescricao;
	}

	public void setIdprescricao(Long idprescricao) {
		this.idprescricao = idprescricao;
	}

	public Prescritor getPrescritor() {
		return prescritor;
	}

	public void setPrescritor(Prescritor prescritor) {
		this.prescritor = prescritor;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(Date data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public String getNome_paciente() {
		return nome_paciente;
	}

	public void setNome_paciente(String nome_paciente) {
		this.nome_paciente = nome_paciente;
	}

	public String getEndereco_paciente() {
		return endereco_paciente;
	}

	public void setEndereco_paciente(String endereco_paciente) {
		this.endereco_paciente = endereco_paciente;
	}

	public String getTelefone_paciente() {
		return telefone_paciente;
	}

	public void setTelefone_paciente(String telefone_paciente) {
		this.telefone_paciente = telefone_paciente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idprescricao == null) ? 0 : idprescricao.hashCode());
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
		Prescricao other = (Prescricao) obj;
		if (idprescricao == null) {
			if (other.idprescricao != null)
				return false;
		} else if (!idprescricao.equals(other.idprescricao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prescricao [idprescricao=" + idprescricao + ", prescritor=" + prescritor + ", nome_paciente="
				+ nome_paciente + ", endereco_paciente=" + endereco_paciente + ", telefone_paciente="
				+ telefone_paciente + ", formula_prescricao=" + formula_prescricao + ", data_cadastro=" + data_cadastro
				+ ", data_alteracao=" + data_alteracao + "]";
	}

}
