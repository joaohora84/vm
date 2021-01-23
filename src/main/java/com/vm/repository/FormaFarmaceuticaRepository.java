package com.vm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vm.model.FormaFamaceutica;

@Repository
@Transactional
public interface FormaFarmaceuticaRepository extends JpaRepository<FormaFamaceutica, Long>{

}
