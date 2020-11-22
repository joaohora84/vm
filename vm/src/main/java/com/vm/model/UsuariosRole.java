package com.vm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "usuarios_role")
@Entity
public class UsuariosRole implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	@Id
	public Long usuario_id;
	
	@Id
	public Long role_id;
	
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	public Long getUsuario_id() {
		return usuario_id;
	}
	
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	
	public Long getRole_id() {
		return role_id;
	}

}
