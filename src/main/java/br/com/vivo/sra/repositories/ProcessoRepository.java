package br.com.vivo.sra.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vivo.sra.entities.Processo;

@Transactional
public interface ProcessoRepository extends JpaRepository<Processo, Long>{ 
}
