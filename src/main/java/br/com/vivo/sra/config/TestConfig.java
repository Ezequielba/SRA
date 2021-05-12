package br.com.vivo.sra.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.entities.Usuario;
import br.com.vivo.sra.repositories.AcessoRepository;
import br.com.vivo.sra.repositories.ProcessoRepository;
import br.com.vivo.sra.repositories.SistemaRepository;
import br.com.vivo.sra.repositories.UsuarioRepository;
import br.com.vivo.sra.services.EngineService;
import br.com.vivo.sra.services.ProcessoService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SistemaRepository sistemaRepository;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Usuario u1 = new Usuario(null, "Usuario_01", "123", true);
		Usuario u2 = new Usuario(null, "Usuario_02", "456", false);
		
		Sistema s1 = new Sistema(null, "x", false, Instant.parse("2020-08-18T18:00:00Z"), u1);
		Sistema s2 = new Sistema(null, "x", false, Instant.parse("2020-08-18T18:00:00Z"), u2);
		
		Acesso a1 = new Acesso(null, "x", "x", "x", "x", "x", "stop.sh", "./start.sh", s1);
		Acesso a2 = new Acesso(null, "x", "x", "x", "xc", "x", "stop.sh", "./start.sh", s2);

		Processo p1 = new Processo(null, "Processo_01", true, true, Instant.parse("2020-08-18T18:00:00Z"), s1, a1);
		Processo p2 = new Processo(null, "Processo_02", true, true, Instant.parse("2020-08-18T18:00:00Z"), s2, a2);
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2));
		sistemaRepository.saveAll(Arrays.asList(s1, s2));
		acessoRepository.saveAll(Arrays.asList(a1, a2));
		processoRepository.saveAll(Arrays.asList(p1));
		processoRepository.saveAll(Arrays.asList(p2));
		
		EngineService engineService = new EngineService();	
			
		while(true) {
		System.out.println("");
		System.out.println("");
		Thread.sleep(5000);
		List<Processo> listProcesso = processoRepository.findAll();
		System.out.println("Monitorando campo de monitoração!!!");
		for(Processo processo: listProcesso) {
			if (!processo.getStatusMonitoracao() && processo.getStatusProcesso()) {
				Acesso acesso = processo.getAcesso();
				engineService.listenEngine(acesso.getUsuario(), acesso.getSenha(), acesso.getIp(), acesso.getDiretorio(), acesso.getStart());
				processo.setStatusMonitoracao(true);
				processoRepository.save(processo);
			}
		}
		}
	}
}
