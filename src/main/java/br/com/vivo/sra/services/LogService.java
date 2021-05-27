package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Log;
import br.com.vivo.sra.repositories.LogRepository;

@Service
public class LogService {
	
	@Autowired
	private LogRepository repository;
	
	public List<Log> findAll(){
		return repository.findAll();
	}
	
	public Log findById(Long id){
		Optional<Log> obj = repository.findById(id);
		return obj.get();
	}
	
	public Log insert(Log obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Log update(Long id, Log obj) {
		Log entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Log entity, Log obj) {
		entity.setProcesso(obj.getProcesso());
		entity.setSistema(obj.getSistema());
		entity.setHostname(obj.getHostname());
		entity.setTipoLog(obj.getTipoLog());
		entity.setMensagem(obj.getMensagem());
		entity.setDataRestart(obj.getDataRestart());
	}
}
