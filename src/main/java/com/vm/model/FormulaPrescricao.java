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
public class FormulaPrescricao implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formula_prescricao_sequence")
	@SequenceGenerator(name = "formula_prescricao_sequence", sequenceName = "formula_prescricao_seq")
	private Long idformulaprescricao;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "prescricao_fk")
	private Prescricao prescricao;
	
	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "formula_fk")
	private Formula formula;

	public Long getIdformulaprescricao() {
		return idformulaprescricao;
	}

	public void setIdformulaprescricao(Long idformulaprescricao) {
		this.idformulaprescricao = idformulaprescricao;
	}

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
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
		result = prime * result + ((idformulaprescricao == null) ? 0 : idformulaprescricao.hashCode());
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
		FormulaPrescricao other = (FormulaPrescricao) obj;
		if (idformulaprescricao == null) {
			if (other.idformulaprescricao != null)
				return false;
		} else if (!idformulaprescricao.equals(other.idformulaprescricao))
			return false;
		return true;
	}
	
	

}
