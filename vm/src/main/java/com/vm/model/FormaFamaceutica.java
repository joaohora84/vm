package com.vm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.SequenceGenerator;

@Entity
public class FormaFamaceutica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forma_farmaceutica_sequence")
	@SequenceGenerator(name = "forma_farmaceutica_sequence", sequenceName = "forma_f_seq")
	private Long idforma_farmaceutica;

	@Column(length = 50)
	private String nome;

	@OneToMany(mappedBy = "forma_farmaceutica", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Formula> formula;

	public void setFormula(List<Formula> formula) {
		this.formula = formula;
	}
	
	public List<Formula> getFormula() {
		return formula;
	}

	public Long getIdforma_farmaceutica() {
		return idforma_farmaceutica;
	}

	public void setIdforma_farmaceutica(Long idforma_farmaceutica) {
		this.idforma_farmaceutica = idforma_farmaceutica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idforma_farmaceutica == null) ? 0 : idforma_farmaceutica.hashCode());
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
		FormaFamaceutica other = (FormaFamaceutica) obj;
		if (idforma_farmaceutica == null) {
			if (other.idforma_farmaceutica != null)
				return false;
		} else if (!idforma_farmaceutica.equals(other.idforma_farmaceutica))
			return false;
		return true;
	}

}
