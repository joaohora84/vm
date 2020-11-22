package com.vm.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.Prescricao;

@Repository
@Transactional
public interface PrescricaoRepository extends JpaRepository<Prescricao, Long>{
	
	@Query("select p from Prescricao p where p.prescritor.id= ?1")
	Page<Prescricao> getPrescricaoPorPrescritor(Long idprescritor, Pageable pageable);
  

}
