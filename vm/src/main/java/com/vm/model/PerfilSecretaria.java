package com.vm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "perfil_secretaria")
public class PerfilSecretaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfilsecretaria_sequence")
	@SequenceGenerator(name = "perfilsecretaria_sequence", sequenceName = "perfilsec_seq")
	private Long idperfilsecretaria;

	@Column(length = 50)
	private String hobby;
	@Column(length = 50)
	private String assuntopredileto;
	@Column(length = 100)
	private String timefutebol;

	/* CARACTERISTICAS PROFISSIONAIS */

	private boolean estudioso;
	private boolean formadoropniao;

	/* PERFIL PSICOLOGICO */

	private boolean obstinado;
	private boolean metodico;
	private boolean sociavel;
	private boolean estavel;
	private boolean afavel;
	private boolean atencioso;
	private boolean desinteressado;
	private boolean questionador;
	private boolean indeciso;
	private boolean irritado;
	private boolean calmo;
	private boolean desagradavel;
	private boolean agradavel;
	private boolean visual;
	private boolean auditivo;
	private boolean cinestesico;
	private boolean inteligente;

	private String observacao;

	@JsonIgnore
	@OneToOne
	@ForeignKey(name = "secretaria_fk")
	private Secretaria secretaria;

	public Long getIdperfilsecretaria() {
		return idperfilsecretaria;
	}

	public void setIdperfilsecretaria(Long idperfilsecretaria) {
		this.idperfilsecretaria = idperfilsecretaria;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getAssuntopredileto() {
		return assuntopredileto;
	}

	public void setAssuntopredileto(String assuntopredileto) {
		this.assuntopredileto = assuntopredileto;
	}

	public String getTimefutebol() {
		return timefutebol;
	}

	public void setTimefutebol(String timefutebol) {
		this.timefutebol = timefutebol;
	}

	public boolean isEstudioso() {
		return estudioso;
	}

	public void setEstudioso(boolean estudioso) {
		this.estudioso = estudioso;
	}

	public boolean isFormadoropniao() {
		return formadoropniao;
	}

	public void setFormadoropniao(boolean formadoropniao) {
		this.formadoropniao = formadoropniao;
	}

	public boolean isObstinado() {
		return obstinado;
	}

	public void setObstinado(boolean obstinado) {
		this.obstinado = obstinado;
	}

	public boolean isMetodico() {
		return metodico;
	}

	public void setMetodico(boolean metodico) {
		this.metodico = metodico;
	}

	public boolean isSociavel() {
		return sociavel;
	}

	public void setSociavel(boolean sociavel) {
		this.sociavel = sociavel;
	}

	public boolean isEstavel() {
		return estavel;
	}

	public void setEstavel(boolean estavel) {
		this.estavel = estavel;
	}

	public boolean isAfavel() {
		return afavel;
	}

	public void setAfavel(boolean afavel) {
		this.afavel = afavel;
	}

	public boolean isAtencioso() {
		return atencioso;
	}

	public void setAtencioso(boolean atencioso) {
		this.atencioso = atencioso;
	}

	public boolean isDesinteressado() {
		return desinteressado;
	}

	public void setDesinteressado(boolean desinteressado) {
		this.desinteressado = desinteressado;
	}

	public boolean isQuestionador() {
		return questionador;
	}

	public void setQuestionador(boolean questionador) {
		this.questionador = questionador;
	}

	public boolean isIndeciso() {
		return indeciso;
	}

	public void setIndeciso(boolean indeciso) {
		this.indeciso = indeciso;
	}

	public boolean isIrritado() {
		return irritado;
	}

	public void setIrritado(boolean irritado) {
		this.irritado = irritado;
	}

	public boolean isCalmo() {
		return calmo;
	}

	public void setCalmo(boolean calmo) {
		this.calmo = calmo;
	}

	public boolean isDesagradavel() {
		return desagradavel;
	}

	public void setDesagradavel(boolean desagradavel) {
		this.desagradavel = desagradavel;
	}

	public boolean isAgradavel() {
		return agradavel;
	}

	public void setAgradavel(boolean agradavel) {
		this.agradavel = agradavel;
	}

	public boolean isVisual() {
		return visual;
	}

	public void setVisual(boolean visual) {
		this.visual = visual;
	}

	public boolean isAuditivo() {
		return auditivo;
	}

	public void setAuditivo(boolean auditivo) {
		this.auditivo = auditivo;
	}

	public boolean isCinestesico() {
		return cinestesico;
	}

	public void setCinestesico(boolean cinestesico) {
		this.cinestesico = cinestesico;
	}

	public boolean isInteligente() {
		return inteligente;
	}

	public void setInteligente(boolean inteligente) {
		this.inteligente = inteligente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idperfilsecretaria == null) ? 0 : idperfilsecretaria.hashCode());
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
		PerfilSecretaria other = (PerfilSecretaria) obj;
		if (idperfilsecretaria == null) {
			if (other.idperfilsecretaria != null)
				return false;
		} else if (!idperfilsecretaria.equals(other.idperfilsecretaria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PerfilSecretaria [idperfilsecretaria=" + idperfilsecretaria + ", hobby=" + hobby + ", assuntopredileto="
				+ assuntopredileto + ", timefutebol=" + timefutebol + ", estudioso=" + estudioso + ", formadoropniao="
				+ formadoropniao + ", obstinado=" + obstinado + ", metodico=" + metodico + ", sociavel=" + sociavel
				+ ", estavel=" + estavel + ", afavel=" + afavel + ", atencioso=" + atencioso + ", desinteressado="
				+ desinteressado + ", questionador=" + questionador + ", indeciso=" + indeciso + ", irritado="
				+ irritado + ", calmo=" + calmo + ", desagradavel=" + desagradavel + ", agradavel=" + agradavel
				+ ", visual=" + visual + ", auditivo=" + auditivo + ", cinestesico=" + cinestesico + ", inteligente="
				+ inteligente + ", observacao=" + observacao + ", secretaria=" + secretaria + "]";
	}
	
 
}
