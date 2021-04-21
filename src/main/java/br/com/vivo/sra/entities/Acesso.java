package br.com.vivo.sra.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Acesso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String hostname;
	private String ip;
	private String usuario;
	private String senha;
	private String diretorio;
	private String stop;
	private String start;
	
	@ManyToOne
	@JoinColumn(name = "sistema_id")
	private Sistema sistema;
	
	@ManyToOne
	@JoinColumn(name = "processo_id")
	private Processo processo;
	
	public Acesso() {
	}

	public Acesso(Long id, String hostname, String ip, String usuario, String senha, String diretorio, String stop, 
			String start, Sistema sistema, Processo processo) {
		super();
		this.id = id;
		this.hostname = hostname;
		this.ip = ip;
		this.usuario = usuario;
		this.senha = senha;
		this.diretorio = diretorio;
		this.stop = stop;
		this.start = start;
		this.sistema = sistema;
		this.processo = processo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public Processo getProcesso() {
		return processo;
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
		Acesso other = (Acesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
