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

import com.vm.model.Visitador;

@Repository
@Transactional(readOnly = true)
public interface VisitadorRepository extends JpaRepository<Visitador, Long>{
	
	@Query("select v from Visitador v where v.nome like %?1%")
	List<Visitador> findVisitadorByName(String nome);
	
	default Page<Visitador> findVisitadorByNamePage(String nome, Pageable pageable) {
		
		Visitador visitador = new Visitador();
		visitador.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Visitador> example = Example.of(visitador, ignoringExampleMatcher);
		
		Page<Visitador> visitadores = findAll(example, pageable);
		
		return visitadores;
		
	}

}
