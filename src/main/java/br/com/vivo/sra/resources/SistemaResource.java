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

import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.services.SistemaService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/sistemas")
public class SistemaResource {
	
	@Autowired
	private SistemaService service;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Sistema>> findAll() {
		List<Sistema> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(value="/{id}")
	public ResponseEntity<Sistema> findById(@PathVariable Long id){
		Sistema obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Sistema> insert(@RequestBody Sistema obj){
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
	public ResponseEntity<Sistema> update(@PathVariable Long id, @RequestBody Sistema obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
}
