package br.com.vivo.sra.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vivo.sra.entities.Processo;

@Transactional
public interface ProcessoRepository extends JpaRepository<Processo, Long>{
	@Query(value = "SELECT * FROM Processo WHERE tipoProcesso_id = :tipoProcessoId AND statusMonitoracao = :statusMonitoracao OR tipoProcesso_id = :tipoProcessoId AND dataAgendamento >= :dataAgendamento",
    nativeQuery = true)
	List <Processo> findByTipoProcesso(
			@Param("tipoProcessoId") Long id,
			@Param("statusMonitoracao") Boolean statusMonitoracao,
			@Param("dataAgendamento") String dataAgendamento);
}
