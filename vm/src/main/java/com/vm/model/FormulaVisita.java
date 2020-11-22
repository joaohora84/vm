package com.vm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class FormulaVisita implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formula_visita_sequence")
	@SequenceGenerator(name = "formula_visita_sequence", sequenceName = "formula_visita_seq")
	private Long idformulavisita;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "visita_fk")
	private Visita visita;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "formula_fk")
	private Formula formula;
	
	

	public Long getIdformulavisita() {
		return idformulavisita;
	}

	public void setIdformulavisita(Long idformulavisita) {
		this.idformulavisita = idformulavisita;
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
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
		result = prime * result + ((idformulavisita == null) ? 0 : idformulavisita.hashCode());
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
		FormulaVisita other = (FormulaVisita) obj;
		if (idformulavisita == null) {
			if (other.idformulavisita != null)
				return false;
		} else if (!idformulavisita.equals(other.idformulavisita))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FormulaVisita [idformulavisita=" + idformulavisita + ", visita=" + visita + ", formula=" + formula
				+ "]";
	}
	
	
	
	

}
