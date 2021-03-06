package br.com.vivo.sra.services;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service
public class EngineService {
	
	BufferedWriter p_stdin;
	
	public String listenEngine(String usuario, String senha, String ip, String diretorio, String start, String stop) {

		try{
	        JSch jsch = new JSch();

	        Session session = jsch.getSession(usuario, ip, 22);
	        session.setPassword(senha);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();
	        System.out.println("Conectou!");
	        System.out.println("");
	        
	        Channel channel=session.openChannel("exec");

	        ((ChannelExec)channel).setCommand("cd " + diretorio + " && " + stop + " && " + start);        
	        
	        channel.setInputStream(null);

	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            if(channel.getExitStatus() == 0) {
	            	System.out.println("Comando executado com sucesso");
	            }else {
	            	return "Falha para executar o comando: " + stop + "ou" +start;
	            }
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	          channel.disconnect();
		      session.disconnect();
	        }
	        System.out.println("");
	        System.out.println("DONE");
	        return "";
	    }catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println(e.getMessage());
	    	return e.getMessage();
	    }
	}
	
	
	public Boolean listenEngine(String usuario, String senha, String ip) {

		try{
	        JSch jsch = new JSch();

	        Session session = jsch.getSession(usuario, ip, 22);
	        session.setPassword(senha);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();
	        return true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println(e.getMessage());
	    	return false;
	    }
	}
	
	@SuppressWarnings("resource")
	public String listenEngineWindows(String Diretorio, String start, String stop) {
	    
		try {
			String NavegaDiretorio = Diretorio.substring(0, 2);
			
		    Process exec = Runtime.getRuntime().exec( "cmd /C "+NavegaDiretorio+" && cd " + Diretorio +"&& "+ stop +"&& "+ start);
		    InputStream in = exec.getInputStream();
		    Scanner scan = new Scanner(in);
		    while( scan.hasNext() ) {
		        System.out.println( scan.nextLine());   
		    }
		    return "";
		     
		} catch (IOException e) {
		    e.printStackTrace();
		    return e.getMessage();
		}
	}
	
}
