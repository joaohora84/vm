package com.vm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "sugestao_formula")
public class SugestaoFormula implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sugestao_formula_sequence")
	@SequenceGenerator(name = "sugestao_formula_sequence", sequenceName = "sugestao_form_seq")
	private Long idsugestao_formula;

	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "formula_fk")
	private Formula formula;

	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "ativo_fk")
	private Ativo ativo;

	public Long getIdsugestao_formula() {
		return idsugestao_formula;
	}

	public void setIdsugestao_formula(Long idsugestao_formula) {
		this.idsugestao_formula = idsugestao_formula;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idsugestao_formula == null) ? 0 : idsugestao_formula.hashCode());
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
		SugestaoFormula other = (SugestaoFormula) obj;
		if (idsugestao_formula == null) {
			if (other.idsugestao_formula != null)
				return false;
		} else if (!idsugestao_formula.equals(other.idsugestao_formula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SusgestaoFormula [idsugestao_formula=" + idsugestao_formula + ", formula=" + formula + ", ativo="
				+ ativo + "]";
	}

}
