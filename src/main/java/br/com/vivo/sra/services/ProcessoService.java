package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.repositories.ProcessoRepository;

@Service
public class ProcessoService {
	
	@Autowired
	private ProcessoRepository repository;
	
	public List<Processo> findAll(){
		return repository.findAll();
	}
	
	public Processo findById(Long id){
		Optional<Processo> obj = repository.findById(id);
		return obj.get();
	}
	
	public Processo insert(Processo obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Processo update(Long id, Processo obj) {
		Processo entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Processo entity, Processo obj) {
		entity.setNome(obj.getNome());
		entity.setStatusProcesso(obj.getStatusProcesso());
	}
	
}
