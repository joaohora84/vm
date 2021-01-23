package com.vm.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.PerfilPrescritor;

@Repository
@Transactional
public interface PerfilPrescritorRepository extends JpaRepository<PerfilPrescritor, Long>{
	
	@Query("select p from PerfilPrescritor p where p.prescritor.idprescritor = ?1")
	public PerfilPrescritor getPerfil(Long idpres);
	
	
}
