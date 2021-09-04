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

import com.vm.model.Prescritor;

@Repository
@Transactional(readOnly = true)
public interface PrescritorRepository extends JpaRepository<Prescritor, Long>{
	
	@Query("select p from Prescritor p where p.nome like %?1%")
	List<Prescritor> findPrescritorByName(String nome);
	
	@Query("select p from Prescritor p where p.clinica.id = ?1")
	public List<Prescritor> getPrescritorClinica(Long clinicaid);
	
	@Query("select p from Prescritor "
			+ "p where p.conselho = ?1 "
			+ "and p.numeroConselho = ?2 "
			+ "and p.especialidade.id = ?3 "
			+ "and p.clinica.id = ?4")
	public List<Prescritor> getPrescritorMista(String conselho, String numeroConselho, Long especialidade, Long clinica);
	
	default Page<Prescritor> findPrescritorByNamePage(String nome, Pageable pageable) {
		
		Prescritor prescritor = new Prescritor();
		prescritor.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Prescritor> example = Example.of(prescritor, ignoringExampleMatcher);
		
		Page<Prescritor> prescritores = findAll(example, pageable);
		
		return prescritores;
		
	}

}
