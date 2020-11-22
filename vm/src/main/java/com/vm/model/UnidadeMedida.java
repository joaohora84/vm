package com.vm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class UnidadeMedida implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_sequence")
	@SequenceGenerator(name = "unidade_sequence", sequenceName = "unidade_seq")
	private Long idunidade;

	@Column(length = 10)
	private String unidade;

	@Column(length = 50)
	private String descricao;

	public Long getIdunidade() {
		return idunidade;
	}

	public void setIdunidade(Long idunidade) {
		this.idunidade = idunidade;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idunidade == null) ? 0 : idunidade.hashCode());
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
		UnidadeMedida other = (UnidadeMedida) obj;
		if (idunidade == null) {
			if (other.idunidade != null)
				return false;
		} else if (!idunidade.equals(other.idunidade))
			return false;
		return true;
	}

}
