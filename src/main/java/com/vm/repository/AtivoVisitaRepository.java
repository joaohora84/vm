package com.vm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.AtivoVisita;

@Repository
@Transactional
public interface AtivoVisitaRepository extends JpaRepository<AtivoVisita, Long> {

	@Query("select a from AtivoVisita a where a.visita.id = ?1")
	public List<AtivoVisita> getAtivoVisita(Long ativoid);

}
