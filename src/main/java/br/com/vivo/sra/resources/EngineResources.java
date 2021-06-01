package br.com.vivo.sra.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.entities.Log;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.repositories.LogRepository;
import br.com.vivo.sra.repositories.ProcessoRepository;
import br.com.vivo.sra.services.EngineService;

@Component
public class EngineResources{
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private LogRepository logRepository;
	
	EngineService engineService = new EngineService();
	
	public EngineResources() {}
	
	public void EngineLinux(List<Processo> listProcessoLinux) {		
			
	}
	
	public void EngineWindows(List<Processo> listProcessoWindows) {
		
	}
}
