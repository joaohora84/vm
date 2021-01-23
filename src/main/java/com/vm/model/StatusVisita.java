package com.vm.model;

public enum StatusVisita {

	AGENDADA("Agendada"),
	REMARCADA("Remarcada"),
	REALIZADA("Realizada"),
	CANCELADA("Cancelada"),
	REMOVIDA("Removida");

	private String nome;

	private StatusVisita(String nome) {

		this.nome = nome;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
