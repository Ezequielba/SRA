package br.com.vivo.sra;

import java.io.BufferedReader;
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
import br.com.vivo.sra.resources.EngineResources;
import br.com.vivo.sra.services.EngineService;
import br.com.vivo.sra.util.CalculaTempo;

@SpringBootApplication
public class sra implements CommandLineRunner{

	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private LogRepository logRepository;
	
	Boolean efetuarRestart = false;
	EngineService engineService = new EngineService();
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(sra.class, args);	
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String SO = System.getProperty("os.name").trim().toUpperCase();
		
		while(true) {
			//EngineResources engineResource = new EngineResources();

			System.out.println("");
			System.out.println("");
			System.out.println("Verificando o campo de monitoração!!!");
			//System.out.println("Data e Hora Atual: "+LocalDateTime.now().toString());
			
			if(SO.startsWith("LINUX")) {

				System.out.println("TIPO DE SO: "+ SO);
				List<Processo> listProcessoLinux = processoRepository.findByTipoProcesso((long) 1, false, HoraAtual(new Date()));
				EngineLinux(listProcessoLinux);

			}else if(SO.startsWith("WINDOWS")){
				
				System.out.println("TIPO DE SO: "+ SO);
				String HostnameSO = "";
				List<Processo> listProcessoWindows = processoRepository.findByTipoProcesso((long) 2, false, HoraAtual(new Date()));
				HostnameSO = new BufferedReader(new 
						InputStreamReader(Runtime.getRuntime().exec( "cmd /C hostname" ).getInputStream())).readLine();
				EngineWindows(listProcessoWindows, HostnameSO);
			}
			else {
				System.out.println("SISTEMA OPERACIONAL NÂO MAPEADO");
				System.out.println(SO);
			}
			Thread.sleep(5000);
		}	
		
	}
	
	public void EngineLinux(List<Processo> listProcessoLinux) throws InterruptedException {	
		//Remover este for antes de subir para produção
		/*for(Processo processo: listProcessoLinux) {
			System.out.println("Nome Processo: " + processo.getNome() +" - "+ processo.getDataAgendamento());
		}*/
		System.out.println("Hora Atual: " + HoraAtual(new Date()));
		for(Processo processo: listProcessoLinux) {
			efetuarRestart = false;
			Acesso acesso = processo.getAcesso();
			Sistema sistema = processo.getSistema();

			if (HoraAtual(new Date()).equals(processo.getDataAgendamento().trim()) && sistema.getstatusSistema()) {
				processo.setStatusMonitoracao(false);
				//Thread.sleep(60000);
			}
			
			if (!processo.getStatusMonitoracao() && processo.getStatusProcesso() && sistema.getstatusSistema()) {
				
				//Logica Tentativa de Restart
				int retornoDiferenca = CalculaTempo.Diferenca(processo.getDataTentativa01() , LocalDateTime.now().toString());
				System.out.println("Diferenca: " + retornoDiferenca);
				if(retornoDiferenca < 30 && processo.getStatusProcesso() || processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
					if(processo.getDataTentativa01() == null || processo.getDataTentativa01() == "" || retornoDiferenca == -1) {
						processo.setDataTentativa01(LocalDateTime.now().toString());
						if(retornoDiferenca == -1) {
							processo.setDataTentativa02(null);
							processo.setDataTentativa03(null);
						}
						processoRepository.save(processo);
						efetuarRestart = true;
					}
					else if(retornoDiferenca != 0 && processo.getDataTentativa02() == null || processo.getDataTentativa02() == "") {
						processo.setDataTentativa02(LocalDateTime.now().toString());
						processoRepository.save(processo); 
						efetuarRestart = true;
					}
					else if(retornoDiferenca != 0 && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
						processo.setDataTentativa03(LocalDateTime.now().toString());
						processoRepository.save(processo);
						efetuarRestart = true;
					}
				}
				else {	
					efetuarRestart = false;
					
					processo.setDataTentativa01(null);
					processo.setDataTentativa02(null);
					processo.setDataTentativa03(null);
					processo.setStatusProcesso(false);
					processo.setStatusMonitoracao(true);
					processo.setObservacao("Reinicialização automatica encerrada apos 3 tentativa,Por favor verifique o processo!");
					processoRepository.save(processo);
					
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "FALHA","Excedeu o tempo limite!", LocalDateTime.now().toString());
					logRepository.save(l1);
				}
				
				if(efetuarRestart) {
					String returnEngine = engineService.listenEngine(acesso.getUsuario(), acesso.getSenha(), acesso.getIp(), processo.getDiretorio(), processo.getStart(), processo.getStop());
					processo.setStatusMonitoracao(true);
					processoRepository.save(processo);
					System.out.println("Efetuado restart no processo: " + processo.getNome());
					if(returnEngine.isEmpty() || returnEngine == "") {
						Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "SUCESSO","Efetuado Start!", LocalDateTime.now().toString());
						logRepository.save(l1);
					}else {
						Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "ERRO", returnEngine, LocalDateTime.now().toString());
						logRepository.save(l1);
					}	
				}
			}
		}	
	}
	
	public void EngineWindows(List<Processo> listProcessoWindows, String HostnameSO) throws InterruptedException {
		//Remover este for antes de subir para produção
		/*for(Processo processo: listProcessoWindows) {
			System.out.println("Nome Processo: " + processo.getNome()+ " - " + processo.getDataAgendamento());	
		}*/
		System.out.println("Hora Atual: " + HoraAtual(new Date()));
		for(Processo processo: listProcessoWindows) {
			efetuarRestart = false;
			Acesso acesso = processo.getAcesso();
			Sistema sistema = processo.getSistema();			
			if (HoraAtual(new Date()).equals(processo.getDataAgendamento().trim()) && acesso.getHostname().toUpperCase().startsWith(HostnameSO.trim().toUpperCase())) {
				processo.setStatusMonitoracao(false);
				//Thread.sleep(60000);
			}
			
			if (!processo.getStatusMonitoracao() &&
					processo.getStatusProcesso() && 
					sistema.getstatusSistema() && 
					acesso.getHostname().toUpperCase().startsWith(HostnameSO.trim().toUpperCase())
				 ) {
				
				// Logica Tentativa de Restart
				int retornoDiferenca = CalculaTempo.Diferenca(processo.getDataTentativa01() , LocalDateTime.now().toString());
				System.out.println("Diferenca: " + retornoDiferenca);
				if(retornoDiferenca < 30 && processo.getStatusProcesso() && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
					if(processo.getDataTentativa01() == null || processo.getDataTentativa01() == "" || retornoDiferenca == -1) {
						processo.setDataTentativa01(LocalDateTime.now().toString());
						if(retornoDiferenca == -1) {
							processo.setDataTentativa02(null);
							processo.setDataTentativa03(null);
						}
						processoRepository.save(processo);
						efetuarRestart = true;
					}
					else if(retornoDiferenca != 0 && processo.getDataTentativa02() == null || processo.getDataTentativa02() == "") {
						processo.setDataTentativa02(LocalDateTime.now().toString());
						processoRepository.save(processo); 
						efetuarRestart = true;
					}
					else if(retornoDiferenca != 0 && processo.getDataTentativa03() == null || processo.getDataTentativa03() == "") {
						processo.setDataTentativa03(LocalDateTime.now().toString());
						processoRepository.save(processo);
						efetuarRestart = true;
					}
				}
				else {
					
					efetuarRestart = false;
					processo.setDataTentativa01(null);
					processo.setDataTentativa02(null);
					processo.setDataTentativa03(null);
					processo.setStatusProcesso(false);
					processo.setStatusMonitoracao(true);
					processo.setObservacao("Reinicialização automatica encerrada apos 3 tentativa,Por favor verifique o processo!");
					processoRepository.save(processo);
					
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "FALHA","Excedeu o tempo limite!", LocalDateTime.now().toString());
					logRepository.save(l1);								
				}

			}
			
			if(efetuarRestart) {
				String returnEngine = engineService.listenEngineWindows(processo.getDiretorio(), processo.getStart(), processo.getStop());
				processo.setStatusMonitoracao(true);
				processoRepository.save(processo);
				System.out.println("Efetuado restart no processo: " + processo.getNome());
				if(returnEngine.isEmpty() || returnEngine == "") {
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "SUCESSO","Efetuado Start!", LocalDateTime.now().toString());
					logRepository.save(l1);
				}else {
					Log l1 = new Log(null, processo.getNome(), sistema.getNome(), acesso.getHostname(), "ERRO", returnEngine, LocalDateTime.now().toString());
					logRepository.save(l1);
				}	
			}
		}
	}
	
	
	
	public String HoraAtual(Date dt) {
		String hora = ""+dt.getHours();
		String minuto = ""+dt.getMinutes();
		String horaAtual = (hora.length() < 2 ? "0" + hora : hora) + ":" + (minuto.length() < 2 ? "0" + minuto : minuto);
		return horaAtual.trim();
	}
}
