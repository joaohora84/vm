package com.vm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class Secretaria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secretaria_sequence")
	@SequenceGenerator(name = "secretaria_sequence", sequenceName = "secretaria_seq")
	private Long idsecretaria;
	
	@Column(length = 100)
	private String nome;
	
	@Column(length = 20)
	private String telefone1;

	@Column(length = 20)
	private String telefone2;
	
	@Column(length = 50)
	private String email;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_nascimento;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "clinica_fk")
	private Clinica clinica;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "prescritor_fk")
	private Prescritor prescritor;
	
	@JsonIgnore
	@OneToOne(mappedBy = "secretaria", orphanRemoval = true, cascade = CascadeType.ALL)
	private PerfilSecretaria perfilsecretaria;		

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	public Long getIdsecretaria() {
		return idsecretaria;
	}

	public void setIdsecretaria(Long idsecretaria) {
		this.idsecretaria = idsecretaria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
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
	
	public Prescritor getPrescritor() {
		return prescritor;
	}

	public void setPrescritor(Prescritor prescritor) {
		this.prescritor = prescritor;
	}

	public PerfilSecretaria getPerfilsecretaria() {
		return perfilsecretaria;
	}

	public void setPerfilsecretaria(PerfilSecretaria perfilsecretaria) {
		this.perfilsecretaria = perfilsecretaria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idsecretaria == null) ? 0 : idsecretaria.hashCode());
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
		Secretaria other = (Secretaria) obj;
		if (idsecretaria == null) {
			if (other.idsecretaria != null)
				return false;
		} else if (!idsecretaria.equals(other.idsecretaria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Secretaria [idsecretaria=" + idsecretaria + ", nome=" + nome + ", telefone1=" + telefone1
				+ ", telefone2=" + telefone2 + ", email=" + email + ", data_nascimento=" + data_nascimento
				+ ", data_cadastro=" + data_cadastro + ", data_alteracao=" + data_alteracao + ", clinica=" + clinica
				+ ", prescritor=" + prescritor + ", perfilsecretaria=" + perfilsecretaria + "]";
	}
	
	
	
	

}
