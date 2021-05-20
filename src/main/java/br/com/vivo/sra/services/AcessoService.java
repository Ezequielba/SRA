package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.repositories.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository repository;
	
	public List<Acesso> findAll(){
		return repository.findAll();
	}
	
	public Acesso findById(Long id){
		Optional<Acesso> obj = repository.findById(id);
		return obj.get();
	}
	
	public Acesso insert(Acesso obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Acesso update(Long id, Acesso obj) {
		Acesso entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Acesso entity, Acesso obj) {
		entity.setHostname(obj.getHostname());
		entity.setIp(obj.getIp());
		entity.setUsuario(obj.getUsuario());
		entity.setSenha(obj.getSenha());
	}
}
