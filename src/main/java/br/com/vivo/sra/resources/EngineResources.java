package br.com.vivo.sra.resources;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.entities.Log;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.repositories.LogRepository;
import br.com.vivo.sra.repositories.ProcessoRepository;
import br.com.vivo.sra.services.EngineService;
import br.com.vivo.sra.services.LogService;
import br.com.vivo.sra.services.ProcessoService;
import br.com.vivo.sra.util.CalculaTempo;

@Service
public class EngineResources {
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	
	
	public EngineResources() {}
	

}
