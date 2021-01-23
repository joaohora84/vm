package com.vm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.FormulaVisita;
import com.vm.model.Visita;

@Repository
@Transactional
public interface FormulaVisitaRepository extends JpaRepository<FormulaVisita, Long>{
	
	@Query("select f from FormulaVisita f where f.visita.id = ?1")
	public List<FormulaVisita> getFormulaVisita(Long visitaid);
	
	@Query("select f from FormulaVisita f where f.formula.id = ?1")
	public List<FormulaVisita> getFormulaVisitaF(Long formulaid);
	
	@Query("select f.formula.idformula from FormulaVisita f where f.visita.id = ?1")
	public List<Long> getFormulaVisitaIdVisita(Long v);
	

}
