package br.com.vivo.sra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vivo.sra.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByNome(String nome); 
}
