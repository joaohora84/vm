package com.vm.model;

public enum StatusCadastro {
	
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String nome;
	
	private StatusCadastro(String nome) {
		
		this.nome = nome;
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
