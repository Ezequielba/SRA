package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.TipoProcesso;
import br.com.vivo.sra.repositories.TipoProcessoRepository;

@Service
public class TipoProcessoService {
	
	@Autowired
	private TipoProcessoRepository repository;
	
	public List<TipoProcesso> findAll(){
		return repository.findAll();
	}
	
	public TipoProcesso findById(Long id){
		Optional<TipoProcesso> obj = repository.findById(id);
		return obj.get();
	}
	
	public TipoProcesso insert(TipoProcesso obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public TipoProcesso update(Long id, TipoProcesso obj) {
		TipoProcesso entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(TipoProcesso entity, TipoProcesso obj) {
		entity.setTipoProcesso(obj.getTipoProcesso());
	}
}
