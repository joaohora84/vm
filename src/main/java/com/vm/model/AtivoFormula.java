package com.vm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Table(name = "ativo_formula")
@Entity
public class AtivoFormula implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ativo_formula_sequence")
	@SequenceGenerator(name = "ativo_formula_sequence", sequenceName = "ativo_formula_seq")
	private Long idativoformula;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "ativo_fk")
	private Ativo ativo;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "formula_fk")
	private Formula formula;
	
	@OneToOne
	@ForeignKey(name = "unidade_fk")
	private UnidadeMedida unidade;
	
	private double dosagem;
	
	public UnidadeMedida getUnidade() {
		return unidade;
	}
	
	public void setUnidade(UnidadeMedida unidade) {
		this.unidade = unidade;
	}
	
	public double getDosagem() {
		return dosagem;
	}
	
	public void setDosagem(double dosagem) {
		this.dosagem = dosagem;
	}

	public Long getIdativoformula() {
		return idativoformula;
	}

	public void setIdativoformula(Long idativoformula) {
		this.idativoformula = idativoformula;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idativoformula == null) ? 0 : idativoformula.hashCode());
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
		AtivoFormula other = (AtivoFormula) obj;
		if (idativoformula == null) {
			if (other.idativoformula != null)
				return false;
		} else if (!idativoformula.equals(other.idativoformula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AtivoFormula [idativoformula=" + idativoformula + ", ativo=" + ativo + ", formula=" + formula
				+ ", unidade=" + unidade + ", dosagem=" + dosagem + "]";
	}

	
}
