package com.vm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class PerfilPrescritor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfilprescritor_sequence")
	@SequenceGenerator(name = "perfilprescritor_sequence", sequenceName = "perfilpres_seq")
	private Long idperfilprescritor;

	@Column(length = 50)
	private String hobby;
	@Column(length = 50)
	private String assuntopredileto;
	@Column(length = 100)
	private String timefutebol;
	@Column(length = 20)
	private String melhorperiodo; // MANHA, TARDE, NOITE
	@Column(length = 20)
	private String tipovisita;// RAPIDA OU DETALHADA

	/* CARACTERISTICAS PROFISSIONAIS */

	private boolean estudioso;
	private boolean formadoropniao;

	@Column(length = 20)
	private String nivelsocioeconomicopacientes;// AUTO, MEDIO, BAIXO
	@Column(length = 20)
	private String prescreveprodutosindustrializados;// SIM, NAO, RARAMENTE
	@Column(length = 20)
	private String prescreveprodutosmanipulados;// SIM, NAO, RARAMENTE
	@Column(length = 20)
	private String perfilprescricoes;// 1 ATIVO, 2 ATIVOS, 3 ATIVOS OU MAIS

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
	@ForeignKey(name = "prescritor_fk")
	private Prescritor prescritor;

	public Long getIdperfilprescritor() {
		return idperfilprescritor;
	}

	public void setIdperfilprescritor(Long idperfilprescritor) {
		this.idperfilprescritor = idperfilprescritor;
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

	public String getMelhorperiodo() {
		return melhorperiodo;
	}

	public void setMelhorperiodo(String melhorperiodo) {
		this.melhorperiodo = melhorperiodo;
	}

	public String getTipovisita() {
		return tipovisita;
	}

	public void setTipovisita(String tipovisita) {
		this.tipovisita = tipovisita;
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

	public String getNivelsocioeconomicopacientes() {
		return nivelsocioeconomicopacientes;
	}

	public void setNivelsocioeconomicopacientes(String nivelsocioeconomicopacientes) {
		this.nivelsocioeconomicopacientes = nivelsocioeconomicopacientes;
	}

	public String getPrescreveprodutosindustrializados() {
		return prescreveprodutosindustrializados;
	}

	public void setPrescreveprodutosindustrializados(String prescreveprodutosindustrializados) {
		this.prescreveprodutosindustrializados = prescreveprodutosindustrializados;
	}

	public String getPrescreveprodutosmanipulados() {
		return prescreveprodutosmanipulados;
	}

	public void setPrescreveprodutosmanipulados(String prescreveprodutosmanipulados) {
		this.prescreveprodutosmanipulados = prescreveprodutosmanipulados;
	}

	public String getPerfilprescricoes() {
		return perfilprescricoes;
	}

	public void setPerfilprescricoes(String perfilprescricoes) {
		this.perfilprescricoes = perfilprescricoes;
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

	public Prescritor getPrescritor() {
		return prescritor;
	}

	public void setPrescritor(Prescritor prescritor) {
		this.prescritor = prescritor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idperfilprescritor == null) ? 0 : idperfilprescritor.hashCode());
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
		PerfilPrescritor other = (PerfilPrescritor) obj;
		if (idperfilprescritor == null) {
			if (other.idperfilprescritor != null)
				return false;
		} else if (!idperfilprescritor.equals(other.idperfilprescritor))
			return false;
		return true;
	}

}
