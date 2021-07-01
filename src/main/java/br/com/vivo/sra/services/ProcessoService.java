package br.com.vivo.sra.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.repositories.ProcessoRepository;


@Service
public class ProcessoService {
	
	@Autowired
	private ProcessoRepository repository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	
	public List<Processo> findAll(){
		return repository.findAll((Sort.by("sistema_id")));
	}
	

	@SuppressWarnings("unchecked")
	public List<Processo> findBySistemaID(Long sistemaID){
		Query query = entityManager.createQuery("SELECT p FROM Processo p Where SISTEMA_ID ="+sistemaID);
		return query.getResultList();
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
		entity.setStatusMonitoracao(obj.getStatusMonitoracao());
		entity.setStatusProcesso(obj.getStatusProcesso());
		entity.setDataProcesso(obj.getDataProcesso());
		entity.setDiretorio(obj.getDiretorio());
		entity.setTipoProcesso(obj.getTipoProcesso());
		entity.setStop(obj.getStop());
		entity.setStart(obj.getStart());
		entity.setTipoProcesso(obj.getTipoProcesso());
		entity.setDataAgendamento(obj.getDataAgendamento());
		entity.setAcesso(obj.getAcesso());
		entity.setSistema(obj.getSistema());
	}
	
}
