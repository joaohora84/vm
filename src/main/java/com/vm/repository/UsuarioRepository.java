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
import com.vm.model.Usuario;

@Repository
@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
	
	@Query("select u from Usuario u where u.nome like %?1%")
	List<Prescritor> findUsuarioByName(String nome);
	
	default Page<Usuario> findUsuarioByNamePage(String nome, Pageable pageable) {
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Usuario> example = Example.of(usuario, ignoringExampleMatcher);
		
		Page<Usuario> usuarios = findAll(example, pageable);
		
		return usuarios;
		
	}
	
	

}
