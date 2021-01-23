package com.vm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.SugestaoFormula;

@Repository
@Transactional
public interface SugestaoFormulaRepository extends JpaRepository<SugestaoFormula, Long>{
	
	@Query("select s from SugestaoFormula s where s.ativo.id = ?1")
	public List<SugestaoFormula> getSugestaoFormula(Long ativoid);

}
