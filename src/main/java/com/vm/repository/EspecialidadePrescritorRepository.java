package com.vm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.Ativo;
import com.vm.model.EspecialidadePrescritor;

@Repository
@Transactional
public interface EspecialidadePrescritorRepository extends JpaRepository<EspecialidadePrescritor, Long>{
	
	@Query("select e from EspecialidadePrescritor e order by e.nome asc")
	List<EspecialidadePrescritor> findAllByOrderBy();
	
	@Query("select e from EspecialidadePrescritor e where e.nome=?1")
	EspecialidadePrescritor getEspecialidadeByName(String nome);
	
	
}
