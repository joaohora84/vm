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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Clinica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinica_sequence")
	@SequenceGenerator(name = "clinica_sequence", sequenceName = "clinica_seq")
	private Long idclinica;

	@Column(length = 80)
	private String nome;

	@Column(length = 80)
	private String razao_social;

	@Column(length = 20)
	private String cnpj;

	@Column(length = 30)
	private String inscricao_estadual;

	@Column(length = 30)
	private String inscricao_municipal;

	@Column(length = 20)
	private String telefone1;

	@Column(length = 20)
	private String telefone2;

	@Column(length = 15)
	private String cep;

	@Column(length = 30)
	private String rua;

	@Column(length = 20)
	private String numero;

	@Column(length = 40)
	private String bairro;

	@Column(length = 4)
	private String uf;

	@Column(length = 50)
	private String cidade;

	@Column(length = 50)
	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	@OneToMany(mappedBy = "clinica", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Secretaria> secretaria;

	@OneToMany(mappedBy = "clinica", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Prescritor> prescritor;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(Date data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Long getIdclinica() {
		return idclinica;
	}

	public void setIdclinica(Long idclinica) {
		this.idclinica = idclinica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazao_social() {
		return razao_social;
	}

	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	public String getInscricao_municipal() {
		return inscricao_municipal;
	}

	public void setInscricao_municipal(String inscricao_municipal) {
		this.inscricao_municipal = inscricao_municipal;
	}

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

	public List<Secretaria> getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(List<Secretaria> secretaria) {
		this.secretaria = secretaria;
	}

	public List<Prescritor> getPrescritor() {
		return prescritor;
	}

	public void setPrescritor(List<Prescritor> prescritor) {
		this.prescritor = prescritor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idclinica == null) ? 0 : idclinica.hashCode());
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
		Clinica other = (Clinica) obj;
		if (idclinica == null) {
			if (other.idclinica != null)
				return false;
		} else if (!idclinica.equals(other.idclinica))
			return false;
		return true;
	}

}
