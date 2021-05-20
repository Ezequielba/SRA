package br.com.vivo.sra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vivo.sra.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long>{

}