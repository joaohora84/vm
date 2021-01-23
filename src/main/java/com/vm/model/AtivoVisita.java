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
@Table(name = "ativo_visita")
public class AtivoVisita implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ativo_visita_sequence")
	@SequenceGenerator(name = "ativo_visita_sequence", sequenceName = "ativo_vis_seq")
	private Long idativovisita;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "visita_fk")
	private Visita visita;

	@JsonIgnore
	@ManyToOne
	@ForeignKey(name = "ativo_fk")
	private Ativo ativo;
	
	

	public Long getIdativovisita() {
		return idativovisita;
	}

	public void setIdativovisita(Long idativovisita) {
		this.idativovisita = idativovisita;
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
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
		result = prime * result + ((idativovisita == null) ? 0 : idativovisita.hashCode());
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
		AtivoVisita other = (AtivoVisita) obj;
		if (idativovisita == null) {
			if (other.idativovisita != null)
				return false;
		} else if (!idativovisita.equals(other.idativovisita))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AtivoVisita [idativovisita=" + idativovisita + ", visita=" + visita + ", ativo=" + ativo + "]";
	}

	

}
