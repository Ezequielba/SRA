package br.com.vivo.sra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.vivo.sra.entities.Acesso;
import br.com.vivo.sra.entities.Log;
import br.com.vivo.sra.entities.Processo;
import br.com.vivo.sra.entities.Sistema;
import br.com.vivo.sra.entities.TipoProcesso;
import br.com.vivo.sra.entities.Usuario;
import br.com.vivo.sra.repositories.AcessoRepository;
import br.com.vivo.sra.repositories.LogRepository;
import br.com.vivo.sra.repositories.ProcessoRepository;
import br.com.vivo.sra.repositories.SistemaRepository;
import br.com.vivo.sra.repositories.TipoProcessoRepository;
import br.com.vivo.sra.repositories.UsuarioRepository;
import br.com.vivo.sra.services.EngineService;
import br.com.vivo.sra.util.CalculaTempo;

@SpringBootApplication
public class sra implements CommandLineRunner{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SistemaRepository sistemaRepository;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private TipoProcessoRepository tipoProcessoRepository;
	
	@Autowired
	private LogRepository logRepository;
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(sra.class, args);	
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String SO = System.getProperty("os.name").trim().toUpperCase();
		
		
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
		Acesso a5 = new Acesso(null, "SERVER-DEBIAN", "192.168.0.112", "server-linux", "admin", s5);
		Acesso a6 = new Acesso(null, "DEXTER-RECOVERY", "192.168.0.117", "Administrator", "Indra@01", s5);
		
		TipoProcesso tp1 = new TipoProcesso(null, "LINUX");
		TipoProcesso tp2 = new TipoProcesso(null, "WINDOWS");
		
		Processo p1 = new Processo(null, "NYX", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/app/javaapp/crm-mq/nyxpr", "stop.sh", "./start.sh", tp1, s1, a1);
		Processo p2 = new Processo(null, "TSERVER", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/users/eoc", "./stop_proc", "./start_proc", tp1, s2, a2);
		Processo p3 = new Processo(null, "STAR", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/STAR", "stop.bat", "start.bat", tp2, s3, a3);
		Processo p4 = new Processo(null, "OSP MQ", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/MAP-GUIDE", "stop.bat", "start.bat", tp2, s1, a1);
		Processo p5 = new Processo(null, "MAP GUIDE", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:/PROCESSOS/MAP-GUIDE", "stop.bat", "start.bat", tp2, s4, a4);	
		Processo p6 = new Processo(null, "SisSRA-0.0.1-SNAPSHOT", true, true, Instant.parse("2020-08-18T18:00:00Z"), "/home/publico/Jar","stop.bat", "./Start.sh", tp1, s5, a5);
		Processo p7 = new Processo(null, "SisSRA-0.0.1-WINDOWS", true, true, Instant.parse("2020-08-18T18:00:00Z"), "C:\\Users\\ELIEL\\Desktop\\SRA-Automacao\\Jar","stop.bat", "start.bat", tp2, s5, a6);
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
		sistemaRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
		acessoRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6));
		tipoProcessoRepository.saveAll(Arrays.asList(tp1, tp2));
		processoRepository.saveAll(Arrays.asList(p1));
		processoRepository.saveAll(Arrays.asList(p2));
		processoRepository.saveAll(Arrays.asList(p3));
		processoRepository.saveAll(Arrays.asList(p4));
		processoRepository.saveAll(Arrays.asList(p5));
		processoRepository.saveAll(Arrays.asList(p6));
		processoRepository.saveAll(Arrays.asList(p7));
		
		/*
		while(true) {
			Boolean efetuarRestart = false;
			EngineService engineService = new EngineService();
			Date dt = new Date();
			
			System.out.println("");
			System.out.println("");
			System.out.println("Verificando o campo de monitoração!!!");
			
			
			String hora = ""+dt.getHours();
			String minuto = ""+dt.getMinutes();
			String horaAtual = (hora.length() < 2 ? "0" + hora : hora) + ":" + (minuto.length() < 2 ? "0" + minuto : minuto);
			horaAtual = horaAtual.trim();
			//System.out.println("Hora atual: " + horaAtual);
			
			if(SO.startsWith("LINUX")) {
				
				System.out.println("TIPO DE SO: "+ SO);
				List<Processo> listProcessoLinux = processoRepository.findByTipoProcesso((long) 1, false, horaAtual);
				
				//Remover este for antes de subir para produção
				for(Processo processo: listProcessoLinux) {
					System.out.println("Nome Processo: " + processo.getNome());
				}
				
				for(Processo processo: listProcessoLinux) {
					efetuarRestart = false;
					Acesso acesso = processo.getAcesso();
					Sistema sistema = processo.getSistema();

					if (horaAtual.equals(processo.getDataAgendamento())) {
						processo.setStatusMonitoracao(false);
						Thread.sleep(60000);
					}
					
					if (!processo.getStatusMonitoracao() && processo.getStatusProcesso() 
							&& sistema.getstatusSistema() || horaAtual.equals(processo.getDataAgendamento())) {
						
						//Logica Tentativa de Restart
						int retornoDiferenca = CalculaTempo.Diferenca(processo.getDataTentativa01() , LocalDateTime.now().toString());
						
						if(retornoDiferenca < 30 && processo.getStatusProcesso() && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
							if(processo.getDataTentativa01() == null || processo.getDataTentativa01() == "") {
								processo.setDataTentativa01(LocalDateTime.now().toString());
								processoRepository.save(processo);
								efetuarRestart = true;
							}
							else if(retornoDiferenca > 0 && processo.getDataTentativa02() == null || processo.getDataTentativa02() == "") {
								processo.setDataTentativa02(LocalDateTime.now().toString());
								processoRepository.save(processo); 
								efetuarRestart = true;
							}
							else if(retornoDiferenca > 0 && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
								processo.setDataTentativa03(LocalDateTime.now().toString());
								processoRepository.save(processo);
								efetuarRestart = true;
							}
						}
						else {
							processo.setDataTentativa01(null);
							processo.setDataTentativa02(null);
							processo.setDataTentativa03(null);
							processo.setStatusProcesso(false);
							processo.setStatusMonitoracao(true);
							processo.setObservacao("Reinicialização automatica encerrada apos 3 tentativa,Por favor verifique o processo!");
							Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "FALHA","Excedeu o tempo limite!", LocalDateTime.now().toString());
							logRepository.saveAll(Arrays.asList(l1));
							processoRepository.save(processo);
							efetuarRestart = false;
						}
						
						if(efetuarRestart) {
							String returnEngine = engineService.listenEngine(acesso.getUsuario(), acesso.getSenha(), acesso.getIp(), processo.getDiretorio(), processo.getStart(), processo.getStop());
							processo.setStatusMonitoracao(true);
							processoRepository.save(processo);
							System.out.println("Efetuado restart no processo: " + processo.getNome());
							if(returnEngine.isEmpty() || returnEngine == "") {
								Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "SUCESSO","Efetuado Start!", LocalDateTime.now().toString());
								logRepository.saveAll(Arrays.asList(l1));
							}else {
								Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "ERRO", returnEngine, LocalDateTime.now().toString());
								logRepository.saveAll(Arrays.asList(l1));
							}	
						}
					}
				}	
			}else if(SO.startsWith("WINDOWS")){
				
				System.out.println("TIPO DE SO: "+ SO);
				String HostnameSO = "";
				List<Processo> listProcessoWindows = processoRepository.findByTipoProcesso((long) 2, false, horaAtual);
				
				//Remover este for antes de subir para produção
				for(Processo processo: listProcessoWindows) {
					System.out.println("Nome Processo: " + processo.getNome());	
				}
				
				try {		
				    HostnameSO = new BufferedReader(new 
							InputStreamReader(Runtime.getRuntime().exec( "cmd /C hostname" ).getInputStream())).readLine();

				}catch(IOException e){
					System.out.println(e.getMessage());  
				}
				
				for(Processo processo: listProcessoWindows) {
					efetuarRestart = false;
					Acesso acesso = processo.getAcesso();
					Sistema sistema = processo.getSistema();
					
					if (horaAtual.equals(processo.getDataAgendamento()) && acesso.getHostname().toUpperCase().startsWith(HostnameSO.trim().toUpperCase())) {
						processo.setStatusMonitoracao(false);
						Thread.sleep(60000);
					}
					
					if (!processo.getStatusMonitoracao() &&
							processo.getStatusProcesso() && 
							sistema.getstatusSistema() && 
							acesso.getHostname().toUpperCase().startsWith(HostnameSO.trim().toUpperCase())
						 ) {
						
						// Logica Tentativa de Restart
						int retornoDiferenca = CalculaTempo.Diferenca(processo.getDataTentativa01() , LocalDateTime.now().toString());
						
						if(retornoDiferenca < 30 && processo.getStatusProcesso() && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
							if(processo.getDataTentativa01() == null || processo.getDataTentativa01() == "") {
								processo.setDataTentativa01(LocalDateTime.now().toString());
								processoRepository.save(processo);
								efetuarRestart = true;
							}
							else if(retornoDiferenca > 0 && processo.getDataTentativa02() == null || processo.getDataTentativa02() == "") {
								processo.setDataTentativa02(LocalDateTime.now().toString());
								processoRepository.save(processo); 
								efetuarRestart = true;
							}
							else if(retornoDiferenca > 0 && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
								processo.setDataTentativa03(LocalDateTime.now().toString());
								processoRepository.save(processo);
								efetuarRestart = true;
							}
						}
						else {
							processo.setDataTentativa01(null);
							processo.setDataTentativa02(null);
							processo.setDataTentativa03(null);
							processo.setStatusProcesso(false);
							processo.setStatusMonitoracao(true);
							processo.setObservacao("Reinicialização automatica encerrada apos 3 tentativa,Por favor verifique o processo!");
							Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "FALHA","Excedeu o tempo limite!", LocalDateTime.now().toString());
							logRepository.saveAll(Arrays.asList(l1));							
							processoRepository.save(processo);
							efetuarRestart = false;
						}
		
					}
					
					if(efetuarRestart) {
						String returnEngine = engineService.listenEngineWindows(processo.getDiretorio(), processo.getStart(), processo.getStop());
						processo.setStatusMonitoracao(true);
						processoRepository.save(processo);
						System.out.println("Efetuado restart no processo: " + processo.getNome());
						if(returnEngine.isEmpty() || returnEngine == "") {
							Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "SUCESSO","Efetuado Start!", LocalDateTime.now().toString());
							logRepository.saveAll(Arrays.asList(l1));
						}else {
							Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "ERRO", returnEngine, LocalDateTime.now().toString());
							logRepository.saveAll(Arrays.asList(l1));
						}	
					}
				}
			}
			else {
				System.out.println("SISTEMA OPERACIONAL NÂO MAPEADO");
				System.out.println(SO);
			}
			Thread.sleep(5000);
		}	
		*/
	}		
}
