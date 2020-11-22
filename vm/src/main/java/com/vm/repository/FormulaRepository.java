package com.vm.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.Formula;

@Repository
@Transactional(readOnly = true)
public interface FormulaRepository extends JpaRepository<Formula, Long> {

	@Query("select f from Formula f where f.nome like %?1%")
	List<Formula> findFormulaByName(String nome);

	@Query("select f from Formula f where f.especialidade.nome like %?1%")
	List<Formula> findFormulaByEspecialidade(String especialidade);

	//@Query("select f from Formula f where f.ativo.id = ?1")
	//public List<Formula> getFormula(Long ativoid);

	default Page<Formula> findFormulaByNamePage(String nome, Pageable pageable) {

		Formula formula = new Formula();
		formula.setNome(nome);

		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		Example<Formula> example = Example.of(formula, ignoringExampleMatcher);

		Page<Formula> formulas = findAll(example, pageable);

		return formulas;

	}

}
