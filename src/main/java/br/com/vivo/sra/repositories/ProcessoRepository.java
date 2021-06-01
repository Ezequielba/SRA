package br.com.vivo.sra.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vivo.sra.entities.Processo;

@Transactional
public interface ProcessoRepository extends JpaRepository<Processo, Long>{
	@Query(value = "SELECT * FROM PROCESSO WHERE TIPO_PROCESSO_ID = :tipoProcessoId AND STATUS_MONITORACAO = :statusMonitoracao",
    nativeQuery = true)
	List <Processo> findByTipoProcesso(
			@Param("tipoProcessoId") Long id,
			@Param("statusMonitoracao") Boolean statusMonitoracao);
	
}
