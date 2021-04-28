package br.com.vivo.sra.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.services.AcessoService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/acessos")
public class AcessoResource {
	
	@Autowired
	private AcessoService service;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Acesso>> findAll() {
		List<Acesso> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(value="/{id}")
	public ResponseEntity<Acesso> findById(@PathVariable Long id){
		Acesso obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Acesso> insert(@RequestBody Acesso obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@CrossOrigin
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@PutMapping(value="/{id}")
	public ResponseEntity<Acesso> update(@PathVariable Long id, @RequestBody Acesso obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
}
