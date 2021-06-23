package br.com.vivo.sra.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vivo.sra.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long>{
	@Query(value = "SELECT * FROM Log WHERE dataRestart >= :dataInicio AND dataRestart <= :dataFim",
		    nativeQuery = true)
			List <Log> findByFiltroLog(
					@Param("dataInicio") Instant dataInicio,
					@Param("dataFim") Instant dataFim);
}