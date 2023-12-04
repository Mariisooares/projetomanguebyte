package com.mangueByte.telemetria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangueByte.telemetria.domain.model.Dados;

@Repository
public interface DadosRepository extends JpaRepository<Dados, Long> {

}
