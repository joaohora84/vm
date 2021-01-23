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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class Prescritor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescritor_sequence")
	@SequenceGenerator(name = "prescritor_sequence", sequenceName = "prescritor_seq")
	private Long idprescritor;

	@Column(length = 100)
	private String nome;

	@Column(length = 20)
	private String telefone1;

	@Column(length = 20)
	private String telefone2;

	@Column(length = 15)
	private String cep;

	@Column(length = 255)
	private String rua;

	@Column(length = 20)
	private String numero;

	@Column(length = 255)
	private String bairro;

	@Column(length = 20)
	private String conselho;

	@Column(length = 20)
	private String numeroConselho;

	@Column(length = 4)
	private String uf;

	@Column(length = 100)
	private String cidade;

	@Column(length = 100)
	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	@JsonIgnore
	@OneToOne(mappedBy = "prescritor", orphanRemoval = true, cascade = CascadeType.ALL)
	private PerfilPrescritor perfilprescritor;

	@OneToOne
	@ForeignKey(name = "especialidade_prescritor_fk")
	private EspecialidadePrescritor especialidade;
	
	@OneToMany(mappedBy = "clinica", orphanRemoval = false, cascade = CascadeType.ALL)
	private List<Secretaria> secretaria;
	
	@ManyToOne
	@ForeignKey(name = "clinica_prescritor_fk")
	private Clinica clinica;
	
	public List<Secretaria> getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(List<Secretaria> secretaria) {
		this.secretaria = secretaria;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}
	
	public Clinica getClinica() {
		return clinica;
	}

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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumeroConselho() {
		return numeroConselho;
	}

	public void setNumeroConselho(String numeroConselho) {
		this.numeroConselho = numeroConselho;
	}

	public EspecialidadePrescritor getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadePrescritor especialidade) {
		this.especialidade = especialidade;
	}

	public Long getIdprescritor() {
		return idprescritor;
	}

	public void setIdprescritor(Long idprescritor) {
		this.idprescritor = idprescritor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getConselho() {
		return conselho;
	}

	public void setConselho(String conselho) {
		this.conselho = conselho;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getCidade() {
		return cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public PerfilPrescritor getPerfilprescritor() {
		return perfilprescritor;
	}

	public void setPerfilprescritor(PerfilPrescritor perfilprescritor) {
		this.perfilprescritor = perfilprescritor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idprescritor == null) ? 0 : idprescritor.hashCode());
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
		Prescritor other = (Prescritor) obj;
		if (idprescritor == null) {
			if (other.idprescritor != null)
				return false;
		} else if (!idprescritor.equals(other.idprescritor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prescritor [idprescritor=" + idprescritor + ", nome=" + nome + ", telefone1=" + telefone1
				+ ", telefone2=" + telefone2 + ", cep=" + cep + ", rua=" + rua + ", numero=" + numero + ", bairro="
				+ bairro + ", conselho=" + conselho + ", numeroConselho=" + numeroConselho + ", uf=" + uf + ", cidade="
				+ cidade + ", email=" + email + ", data_cadastro=" + data_cadastro + ", data_alteracao="
				+ data_alteracao + ", perfilprescritor=" + perfilprescritor + ", especialidade=" + especialidade
				+ ", clinica=" + clinica + "]";
	}
	
	

}
