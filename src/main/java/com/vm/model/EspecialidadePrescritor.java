package com.vm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class EspecialidadePrescritor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "especialidade_prescritor_sequence")
	@SequenceGenerator(name = "especialidade_prescritor_sequence", sequenceName = "espec_seq")
	private Long idespecialidade;

	@Column(length = 30)
	private String nome;

	public Long getIdespecialidade() {
		return idespecialidade;
	}

	public void setIdespecialidade(Long idespecialidade) {
		this.idespecialidade = idespecialidade;
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
		result = prime * result + ((idespecialidade == null) ? 0 : idespecialidade.hashCode());
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
		EspecialidadePrescritor other = (EspecialidadePrescritor) obj;
		if (idespecialidade == null) {
			if (other.idespecialidade != null)
				return false;
		} else if (!idespecialidade.equals(other.idespecialidade))
			return false;
		return true;
	}

}
