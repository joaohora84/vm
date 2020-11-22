package com.vm.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vm.model.Clinica;
import com.vm.model.Secretaria;

@Repository
@Transactional
public interface SecretariaRepository extends JpaRepository<Secretaria, Long>{
	
	@Query("select s from Secretaria s where s.clinica.id = ?1")
	public List<Secretaria> getSecretariaClinica(Long clinicaid);
	
	@Query("select s from Secretaria s where s.nome like %?1%")
	List<Clinica> findSecretariaByName(String nome);
	
	default Page<Secretaria> findSecretariaByNamePage(String nome, Pageable pageable) {
		
		Secretaria secretaria = new Secretaria();
		secretaria.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Secretaria> example = Example.of(secretaria, ignoringExampleMatcher);
		
		Page<Secretaria> secretarias = findAll(example, pageable);
		
		return secretarias;
		
	}

}
