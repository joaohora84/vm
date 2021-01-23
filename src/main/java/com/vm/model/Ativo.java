package com.vm.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "ativo")
public class Ativo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ativo_sequence")
	@SequenceGenerator(name = "ativo_sequence", sequenceName = "ativo_seq")
	private Long idativo;

	@Column(length = 50)
	private String nome;

	@Column(length = 50)
	private String sinonimo;

	private double dosagem_usual;

	private double dosagem_minima;

	private double dosagem_maxima;

	@Column(length = 1000)
	private String indicacao;

	@Column(length = 1000)
	private String beneficios;

	@Column(length = 1000)
	private String mecanismo_acao;

	@Column(length = 1000)
	private String estudos;

	@Column(length = 1000)
	private String contra_indicacao;

	@Lob
	private byte[] fichatecnica;

	@Column(length = 20)
	private String tipofichatecnica;

	@Column(length = 50)
	private String nomefichatecnica;

	private String observacao;

	@OneToMany
	private List<EspecialidadePrescritor> especialidades;

	/*
	 * @OneToOne
	 * 
	 * @ForeignKey(name = "especialidade_fk") private EspecialidadePrescritor
	 * especialidade;
	 */

	@OneToOne
	@ForeignKey(name = "unidade_fk")
	private UnidadeMedida unidade;

	@JsonIgnore
	@OneToMany(mappedBy = "ativo", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<SugestaoFormula> sugestao_formulas;

	@JsonIgnore
	@OneToMany(mappedBy = "ativo", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<AtivoVisita> ativo_visita;

	@JsonIgnore
	@OneToMany(mappedBy = "ativo", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<AtivoFormula> ativo_formula;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	public void setUnidade(UnidadeMedida unidade) {
		this.unidade = unidade;
	}

	public UnidadeMedida getUnidade() {
		return unidade;
	}

	public Long getIdativo() {
		return idativo;
	}

	public void setIdativo(Long idativo) {
		this.idativo = idativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSinonimo() {
		return sinonimo;
	}

	public void setSinonimo(String sinonimo) {
		this.sinonimo = sinonimo;
	}

	public double getDosagem_usual() {
		return dosagem_usual;
	}

	public void setDosagem_usual(double dosagem_usual) {
		this.dosagem_usual = dosagem_usual;
	}

	public double getDosagem_minima() {
		return dosagem_minima;
	}

	public void setDosagem_minima(double dosagem_minima) {
		this.dosagem_minima = dosagem_minima;
	}

	public double getDosagem_maxima() {
		return dosagem_maxima;
	}

	public void setDosagem_maxima(double dosagem_maxima) {
		this.dosagem_maxima = dosagem_maxima;
	}

	public String getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public String getMecanismo_acao() {
		return mecanismo_acao;
	}

	public void setMecanismo_acao(String mecanismo_acao) {
		this.mecanismo_acao = mecanismo_acao;
	}

	public String getEstudos() {
		return estudos;
	}

	public void setEstudos(String estudos) {
		this.estudos = estudos;
	}

	public String getContra_indicacao() {
		return contra_indicacao;
	}

	public void setContra_indicacao(String contra_indicacao) {
		this.contra_indicacao = contra_indicacao;
	}

	public byte[] getFichatecnica() {
		return fichatecnica;
	}

	public void setFichatecnica(byte[] fichatecnica) {
		this.fichatecnica = fichatecnica;
	}

	public String getTipofichatecnica() {
		return tipofichatecnica;
	}

	public void setTipofichatecnica(String tipofichatecnica) {
		this.tipofichatecnica = tipofichatecnica;
	}

	public String getNomefichatecnica() {
		return nomefichatecnica;
	}

	public void setNomefichatecnica(String nomefichatecnica) {
		this.nomefichatecnica = nomefichatecnica;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SugestaoFormula> getSugestao_formulas() {
		return sugestao_formulas;
	}

	public void setSugestao_formulas(List<SugestaoFormula> sugestao_formulas) {
		this.sugestao_formulas = sugestao_formulas;
	}

	public List<AtivoVisita> getAtivo_visita() {
		return ativo_visita;
	}

	public void setAtivo_visita(List<AtivoVisita> ativo_visita) {
		this.ativo_visita = ativo_visita;
	}

	public List<AtivoFormula> getAtivo_formula() {
		return ativo_formula;
	}

	public void setAtivo_formula(List<AtivoFormula> ativo_formula) {
		this.ativo_formula = ativo_formula;
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

	public List<EspecialidadePrescritor> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<EspecialidadePrescritor> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idativo == null) ? 0 : idativo.hashCode());
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
		Ativo other = (Ativo) obj;
		if (idativo == null) {
			if (other.idativo != null)
				return false;
		} else if (!idativo.equals(other.idativo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ativo [idativo=" + idativo + ", nome=" + nome + ", sinonimo=" + sinonimo + ", dosagem_usual="
				+ dosagem_usual + ", dosagem_minima=" + dosagem_minima + ", dosagem_maxima=" + dosagem_maxima
				+ ", indicacao=" + indicacao + ", beneficios=" + beneficios + ", mecanismo_acao=" + mecanismo_acao
				+ ", estudos=" + estudos + ", contra_indicacao=" + contra_indicacao + ", fichatecnica="
				+ Arrays.toString(fichatecnica) + ", tipofichatecnica=" + tipofichatecnica + ", nomefichatecnica="
				+ nomefichatecnica + ", observacao=" + observacao + ", especialidades=" + especialidades + ", unidade="
				+ unidade + ", sugestao_formulas=" + sugestao_formulas + ", ativo_visita=" + ativo_visita
				+ ", ativo_formula=" + ativo_formula + ", data_cadastro=" + data_cadastro + ", data_alteracao="
				+ data_alteracao + "]";
	}

}
