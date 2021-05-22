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
import br.com.vivo.sra.entities.Log;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.entities.Usuario;
import br.com.vivo.sra.repositories.AcessoRepository;
import br.com.vivo.sra.repositories.LogRepository;
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
	
	@Autowired
	private LogRepository logRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Usuario u1 = new Usuario(null, "Usuario_01", "123", true);
		Usuario u2 = new Usuario(null, "Usuario_02", "456", false);
		Usuario u3 = new Usuario(null, "Doctor.Dexter", "654321", false);
		
		Sistema s1 = new Sistema(null, "SCQLA", true, Instant.parse("2020-08-18T18:00:00Z"), u1);
		Sistema s2 = new Sistema(null, "EOC", true, Instant.parse("2020-08-18T18:00:00Z"), u2);
		Sistema s3 = new Sistema(null, "SND", true, Instant.parse("2020-08-18T18:00:00Z"), u1);
		Sistema s4 = new Sistema(null, "OSP", true, Instant.parse("2020-08-18T18:00:00Z"), u1);
		Sistema s5 = new Sistema(null, "SRA", true, Instant.parse("2020-08-18T18:00:00Z"), u3);
		
		Acesso a1 = new Acesso(null, "brtlvlts2695sl", "10.240.62.130", "scqla", "vivo@2016", s1);
		Acesso a2 = new Acesso(null, "SOCBK", "10.20.142.62", "eoc", "vivo@eoc", s2);
		Acesso a3 = new Acesso(null, "BRTLVBGS0192SL", "10.240.3.165", "Rede", "Rede", s2);
		Acesso a4 = new Acesso(null, "BRTLVBGS0035SL", "10.238.60.117", "Rede", "Rede", s4);
		Acesso a5 = new Acesso(null, "SERVER-DEBIAN", "192.168.0.106", "server-linux", "admin", s5);
		
		Processo p1 = new Processo(null, "NYX", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/app/javaapp/crm-mq/nyxpr", "stop.sh", "./start.sh", s1, a1);
		Processo p2 = new Processo(null, "TSERVER", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/users/eoc", "./stop_proc", "./start_proc", s2, a2);
		Processo p3 = new Processo(null, "STAR", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/STAR", "stop.bat", "start.bat", s3, a3);
		Processo p4 = new Processo(null, "OSP MQ", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/MAP-GUIDE", "stop.bat", "start.bat", s1, a1);
		Processo p5 = new Processo(null, "MAP GUIDE", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/MAP-GUIDE", "stop.bat", "start.bat", s4, a4);	
		Processo p6 = new Processo(null, "SisSRA-0.0.1-SNAPSHOT", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/home/publico/Jar", "stop.bat", "./Start.sh", s5, a5);
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
		sistemaRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
		acessoRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5));
		processoRepository.saveAll(Arrays.asList(p1));
		processoRepository.saveAll(Arrays.asList(p2));
		processoRepository.saveAll(Arrays.asList(p3));
		processoRepository.saveAll(Arrays.asList(p4));
		processoRepository.saveAll(Arrays.asList(p5));
		processoRepository.saveAll(Arrays.asList(p6));
		
		EngineService engineService = new EngineService();			
			
		while(true) {
		System.out.println("");
		System.out.println("");
		Thread.sleep(5000);
		List<Processo> listProcesso = processoRepository.findAll();
		System.out.println("Verificando o campo de monitoração!!!");
		
		for(Processo processo: listProcesso) {
			Acesso acesso = processo.getAcesso();
			Sistema sistema = processo.getSistema();
			System.out.println("PASSEI");
			System.out.println(!processo.getStatusMonitoracao() && processo.getStatusProcesso() && sistema.getstatusSistema());
			
			if (!processo.getStatusMonitoracao() && processo.getStatusProcesso() && sistema.getstatusSistema()) {
				String returnEngine = engineService.listenEngine(acesso.getUsuario(), acesso.getSenha(), acesso.getIp(), processo.getDiretorio(), processo.getStart());
				processo.setStatusMonitoracao(true);
				processoRepository.save(processo);
				System.out.println("Efetuado restart no processo: " + processo.getNome());
				System.out.println(s1.getNome() + p1.getNome() + Instant.parse("2020-08-18T18:00:00Z") + a1.getHostname());
				if(returnEngine.isEmpty() || returnEngine == "") {
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "SUCESSO", Instant.now());
					logRepository.saveAll(Arrays.asList(l1));
				}else {
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), returnEngine, Instant.now());
					logRepository.saveAll(Arrays.asList(l1));
				}
				
			}
		}
		}
		
	}
}
