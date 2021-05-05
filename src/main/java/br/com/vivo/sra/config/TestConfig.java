package br.com.vivo.sra.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.entities.Usuario;
import br.com.vivo.sra.repositories.AcessoRepository;
import br.com.vivo.sra.repositories.ProcessoRepository;
import br.com.vivo.sra.repositories.SistemaRepository;
import br.com.vivo.sra.repositories.UsuarioRepository;

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
		
		/* Script para formatar a data */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime agora = LocalDateTime.now();
		String agoraFormatado = agora.format(formatter);
		
		Usuario u1 = new Usuario(null, "Usuario_01", "123", true);
		Usuario u2 = new Usuario(null, "Usuario_02", "456", false);
		
		Sistema s1 = new Sistema(null, "Sistema_01", false, agoraFormatado, u1);
		Sistema s2 = new Sistema(null, "Sistema_02", false, agoraFormatado, u2);
		//Sistema s3 = new Sistema(null, "EOC", false, agoraFormatado, u2);
		
		Acesso a1 = new Acesso(null, "HOST_01", "192.168.0.1", "usuario_host", "123", "/home/teste", "stop.sh", "start.sh", s1);

		Processo p1 = new Processo(null, "Processo_01", true, Instant.parse("2020-08-18T18:00:00Z"), s1, a1);
		Processo p2 = new Processo(null, "Processo_02", true, Instant.parse("2020-08-18T18:00:00Z"), s2, a1);
		Processo p3 = new Processo(null, "Processo_03", true, Instant.parse("2020-08-18T18:00:00Z"), s2, a1);
		Processo p4 = new Processo(null, "Processo_04", true, Instant.parse("2020-08-18T18:00:00Z"), s1, a1);
		Processo p5 = new Processo(null, "Processo_05", true, Instant.parse("2020-08-18T18:00:00Z"), s1, a1);
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2));
		sistemaRepository.saveAll(Arrays.asList(s1, s2));
		acessoRepository.saveAll(Arrays.asList(a1));
		processoRepository.saveAll(Arrays.asList(p1));
		processoRepository.saveAll(Arrays.asList(p2));
		processoRepository.saveAll(Arrays.asList(p3));
		processoRepository.saveAll(Arrays.asList(p4));
		processoRepository.saveAll(Arrays.asList(p5));
				
	}
}
