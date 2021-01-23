package com.vm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.FormulaPrescricao;

@Repository
@Transactional
public interface FormulaPrescricaoRepository extends JpaRepository<FormulaPrescricao, Long>{
	
	
	@Query("select p from FormulaPrescricao p where p.formula.id = ?1")
	public List<FormulaPrescricao> getFormulaPrescricao(Long formulaid);
	
	@Query("select p from FormulaPrescricao p where p.prescricao.id = ?1")
	public List<FormulaPrescricao> getFormulaPrescricaoPorPrescricao(Long prescricaoid);

}
