package br.com.vivo.sra.services;
import java.io.InputStream;

import com.jcraft.jsch.*;

public class EngineService {
	
	public String listenEngine(String usuario, String senha, String ip, String diretorio, String start) {

		try{
	        JSch jsch = new JSch();

	        Session session = jsch.getSession(usuario, ip, 22);
	        session.setPassword(senha);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();
	        System.out.println("Conectou!");
	        System.out.println("");
	        
	        Channel channel=session.openChannel("exec");

	        ((ChannelExec)channel).setCommand("cd " + diretorio + " && " + start);        
	        
	        channel.setInputStream(null);
	        
	        // Investigar essa linha abaixo:
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
	
}
