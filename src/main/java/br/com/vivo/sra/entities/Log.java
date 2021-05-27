package br.com.vivo.sra.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Log implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String processo;
	String sistema;
	String hostname;
	String tipoLog;
	String mensagem;
	Instant dataRestart;
	
	public Log() {
	}
	
	public Log(Long id, String processo, String sistema, String hostname, String tipoLog, String mensagem, Instant dataRestart) {
		super();
		this.id= id;
		this.processo = processo;
		this.sistema = sistema;
		this.hostname = hostname;
		this.tipoLog = tipoLog;
		this.mensagem = mensagem;
		this.dataRestart = dataRestart;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public String getTipoLog() {
		return tipoLog;
	}

	public void setTipoLog(String tipoLog) {
		this.tipoLog = tipoLog;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Instant getDataRestart() {
		return dataRestart;
	}

	public void setDataRestart(Instant dataRestart) {
		this.dataRestart = dataRestart;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
