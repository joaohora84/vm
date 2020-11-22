package com.vm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.StatusVisita;
import com.vm.model.Visita;

@Repository
@Transactional(readOnly = true)
public interface VisitaRepository extends JpaRepository<Visita, Long> {
	
	

	@Query("select v from Visita v where v.visitador.nome like %?1%")
	List<Visita> findVisitaByVisitador(String nome);
	
	@Query("select v from Visita v where v.prescritor.id= ?1")
	List<Visita> findVisitaByPrescritor(Long idprescritor);
	
	@Query("select v from Visita v where v.data_agendamento between ?1 and ?2")
	List<Visita> findVisitaByPeriodo(Date inicio, Date fim);
	
	@Query("select v from Visita v where v.statusvisita like ?1")
	Page<Visita> findVisitaByStatus(StatusVisita status, Pageable pageable);
	
	@Query("select v from Visita v where v.prescritor.id= ?1")
	Page<Visita> getVisitaPorPrescritor(Long idprescritor, Pageable pageable);
	
	@Query("select v from Visita v where v.prescritor.id= ?1 and v.statusvisita= ?2")
	Page<Visita> getVisitaPrescritorAndStatus(Long idprescritor, StatusVisita status, Pageable pageable);
	
	
	
	

}
