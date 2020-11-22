package com.vm.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vm.model.Paciente;

@Repository
@Transactional
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

}
