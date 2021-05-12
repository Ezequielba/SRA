package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.repositories.SistemaRepository;

@Service
public class SistemaService {
	
	@Autowired
	private SistemaRepository repository;
	
	public List<Sistema> findAll(){
		return repository.findAll();
	}
	
	public Sistema findById(Long id){
		Optional<Sistema> obj = repository.findById(id);
		return obj.get();
	}
	
	public Sistema insert(Sistema obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Sistema update(Long id, Sistema obj) {
		Sistema entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Sistema entity, Sistema obj) {
		entity.setNome(obj.getNome());
		entity.setdataAtualizacao(obj.getdataAtualizacao());
		entity.setstatusSistema(obj.getstatusSistema());
		entity.setUsuario(obj.getUsuario());
	}
}
