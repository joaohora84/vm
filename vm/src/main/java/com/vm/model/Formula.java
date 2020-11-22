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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "formula")
public class Formula implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formula_sequence")
	@SequenceGenerator(name = "formula_sequence", sequenceName = "formula_seq")
	private Long idformula;

	@Column(length = 50)
	private String nome;

	@Column(length = 20)
	private String codigo_externo;

	private double valor;

	@OneToOne
	@ForeignKey(name = "especialidade_fk")
	private EspecialidadePrescritor especialidade;

	@ManyToOne
	@ForeignKey(name = "forma_farmaceutica_fk")
	private FormaFamaceutica forma_farmaceutica;

	@Column(columnDefinition = "text")
	private String foto;

	private String posologia;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_alteracao;

	@JsonIgnore
	@OneToMany(mappedBy = "formula", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<FormulaVisita> formula_visita;

	@JsonIgnore
	@OneToMany(mappedBy = "formula", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<FormulaPrescricao> formula_prescricao;

	@JsonIgnore
	@OneToMany(mappedBy = "formula", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<AtivoFormula> ativo_formula;

	public void setAtivo_formula(List<AtivoFormula> ativo_formula) {
		this.ativo_formula = ativo_formula;
	}

	public List<AtivoFormula> getAtivo_formula() {
		return ativo_formula;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setForma_farmaceutica(FormaFamaceutica forma_farmaceutica) {
		this.forma_farmaceutica = forma_farmaceutica;
	}

	public FormaFamaceutica getForma_farmaceutica() {
		return forma_farmaceutica;
	}

	public EspecialidadePrescritor getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadePrescritor especialidade) {
		this.especialidade = especialidade;
	}

	public void setFormula_prescricao(List<FormulaPrescricao> formula_prescricao) {
		this.formula_prescricao = formula_prescricao;
	}

	public List<FormulaPrescricao> getFormula_prescricao() {
		return formula_prescricao;
	}

	public List<FormulaVisita> getFormula_visita() {
		return formula_visita;
	}

	public void setFormula_visita(List<FormulaVisita> formula_visita) {
		this.formula_visita = formula_visita;
	}

	public Long getIdformula() {
		return idformula;
	}

	public void setIdformula(Long idformula) {
		this.idformula = idformula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo_externo() {
		return codigo_externo;
	}

	public void setCodigo_externo(String codigo_externo) {
		this.codigo_externo = codigo_externo;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFoto() {
		return foto;
	}

	public String getPosologia() {
		return posologia;
	}

	public void setPosologia(String posologia) {
		this.posologia = posologia;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public Date getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(Date data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idformula == null) ? 0 : idformula.hashCode());
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
		Formula other = (Formula) obj;
		if (idformula == null) {
			if (other.idformula != null)
				return false;
		} else if (!idformula.equals(other.idformula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Formula [idformula=" + idformula + ", nome=" + nome + ", codigo_externo=" + codigo_externo + ", valor="
				+ valor + ", especialidade=" + especialidade + ", forma_farmaceutica=" + forma_farmaceutica + ", foto="
				+ foto + ", posologia=" + posologia + ", data_cadastro=" + data_cadastro + ", data_alteracao="
				+ data_alteracao + ", formula_visita=" + formula_visita + ", formula_prescricao=" + formula_prescricao
				+ ", ativo_formula=" + ativo_formula + "]";
	}

	

}
