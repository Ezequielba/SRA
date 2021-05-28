package br.com.vivo.sra.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vivo.sra.entities.Processo;

@Transactional
public interface ProcessoRepository extends JpaRepository<Processo, Long>{
	@Query(value = "SELECT * FROM PROCESSO WHERE TIPO_PROCESSO_ID = ?1",
    nativeQuery = true)
	List <Processo> findByTipoProcesso(Long id);
	
}
