package br.com.vivo.sra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vivo.sra.entities.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long>{

}
