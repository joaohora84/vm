package com.vm.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.Clinica;
import com.vm.model.EspecialidadePrescritor;
import com.vm.model.Prescritor;

@Repository
@Transactional(readOnly = true)
public interface PrescritorRepository extends JpaRepository<Prescritor, Long>, QueryByExampleExecutor<Prescritor> {
	
	@Query("select p from Prescritor p where p.clinica.id = ?1")
	List<Prescritor> getPrescritorClinica(Long clinica);

	default Page<Prescritor> findPrescritorByNamePage(String nome, Pageable pageable) {

		Prescritor prescritor = new Prescritor();
		prescritor.setNome(nome);

		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		Example<Prescritor> example = Example.of(prescritor, ignoringExampleMatcher);

		Page<Prescritor> prescritores = findAll(example, pageable);

		return prescritores;

	}

	default Page<Prescritor> getPrescritores(String nome, String conselho, String numeroConselho,
			Long especialidade, Long clinica, Pageable pageable) {

		Prescritor p = new Prescritor();
		p.setNome(nome);
		p.setConselho(conselho);
		p.setNumeroConselho(numeroConselho);

		EspecialidadePrescritor e = new EspecialidadePrescritor();
		e.setIdespecialidade(especialidade);

		p.setEspecialidade(e);

		Clinica c = new Clinica();
		c.setIdclinica(clinica);

		p.setClinica(c);

		ExampleMatcher em = ExampleMatcher.matchingAll().withIgnoreNullValues()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("conselho", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("numeroConselho", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("especialidade", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("clinica", ExampleMatcher.GenericPropertyMatchers.contains());

		Example<Prescritor> example = (Example<Prescritor>) Example.of(p, em);

		Page<Prescritor> prescritores = findAll(example, pageable);

		return prescritores;
	}

}
