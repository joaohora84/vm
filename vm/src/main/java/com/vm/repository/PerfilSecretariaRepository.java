package com.vm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.PerfilSecretaria;

@Repository
@Transactional
public interface PerfilSecretariaRepository extends JpaRepository<PerfilSecretaria, Long>{
	
	@Query("select p from PerfilSecretaria p where p.secretaria.idsecretaria = ?1")
	public PerfilSecretaria getPerfil(Long idsec);

}
