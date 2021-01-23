package com.vm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.AtivoFormula;
import com.vm.model.Formula;

@Repository
@Transactional
public interface AtivoFormulaRepository extends JpaRepository<AtivoFormula, Long>{
	
	@Query("select a from AtivoFormula a where a.formula.id = ?1")
	public List<AtivoFormula> getAtivoFormula(Long idform);
	
	@Query("select a from AtivoFormula a where a.ativo.id = ?1")
	public List<AtivoFormula> getAtivoFormulaPorAtivo(Long idativo);
	
	@Query("select a.formula from AtivoFormula a where a.ativo.id = ?1")
	public List<Formula> getFormulaPorAtivo(Long idativo);

}
