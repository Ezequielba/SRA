package br.com.vivo.sra.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Processo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Boolean statusMonitoracao;
	private Boolean statusProcesso;
	private Instant dataProcesso;
	private String diretorio;
	private String stop;
	private String start;
	private String observacao;
	private String dataAgendamento;
	private Instant dataTentativa01;
	private Instant dataTentativa02;
	private Instant dataTentativa03;
	
	@ManyToOne
	@JoinColumn(name = "tipoProcesso_id", referencedColumnName="id")
	private TipoProcesso tipoProcesso;
	
	@ManyToOne
	@JoinColumn(name = "sistema_id", referencedColumnName="id")
	private Sistema sistema;
	
	@ManyToOne
	@JoinColumn(name = "acesso_id", referencedColumnName="id")
	private Acesso acesso;
	
	public Processo() {
	}
	
	public Processo(Long id, String nome, Boolean statusMonitoracao, Boolean statusProcesso, Instant dataProcesso, String diretorio, String stop, 
			String start, TipoProcesso tipoProcesso, Sistema sistema, Acesso acesso) {
		super();
		this.id = id;
		this.nome = nome;
		this.statusMonitoracao = statusMonitoracao;
		this.statusProcesso = statusProcesso;
		this.dataProcesso = dataProcesso;
		this.diretorio = diretorio;
		this.stop = stop;
		this.start = start;
		this.tipoProcesso = tipoProcesso;
		this.sistema = sistema;
		this.acesso = acesso;
	}	
	/*
	public Processo(Long id, String nome, Boolean statusMonitoracao, Boolean statusProcesso, Instant dataProcesso, String diretorio, String stop, 
			String start, String observacao, Instant dataTentativa01, Instant dataTentativa02, Instant dataTentativa03, Sistema sistema, Acesso acesso) {
		super();
		this.id = id;
		this.nome = nome;
		this.statusMonitoracao = statusMonitoracao;
		this.statusProcesso = statusProcesso;
		this.dataProcesso = dataProcesso;
		this.diretorio = diretorio;
		this.stop = stop;
		this.start = start;
		this.observacao = observacao;
		this.dataTentativa01 = dataTentativa01;
		this.dataTentativa02 = dataTentativa02;
		this.dataTentativa03 = dataTentativa03;
		this.sistema = sistema;
		this.acesso = acesso;
	}*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Boolean getStatusMonitoracao() {
		return statusMonitoracao;
	}

	public void setStatusMonitoracao(Boolean statusMonitoracao) {
		this.statusMonitoracao = statusMonitoracao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getStatusProcesso() {
		return statusProcesso;
	}

	public void setStatusProcesso(Boolean statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	
	public Instant getDataProcesso() {
		return dataProcesso;
	}

	public void setDataProcesso(Instant dataProcesso) {
		this.dataProcesso = dataProcesso;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Instant getDataTentativa01() {
		return dataTentativa01;
	}

	public void setDataTentativa01(Instant dataTentativa01) {
		this.dataTentativa01 = dataTentativa01;
	}

	public Instant getDataTentativa02() {
		return dataTentativa02;
	}

	public void setDataTentativa02(Instant dataTentativa02) {
		this.dataTentativa02 = dataTentativa02;
	}

	public Instant getDataTentativa03() {
		return dataTentativa03;
	}

	public void setDataTentativa03(Instant dataTentativa03) {
		this.dataTentativa03 = dataTentativa03;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public String getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(String dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public Acesso getAcesso() {
		return acesso;
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
		Processo other = (Processo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
