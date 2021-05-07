package br.com.vivo.sra.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sistema implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Boolean statusSistema;
	private Instant dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;	
	
	@OneToMany(mappedBy = "sistema")
	private List<Processo> processos = new ArrayList<>();
	
	public Sistema() {
	}

	public Sistema(Long id, String nome, Boolean statusSistema, Instant dataAtualizacao, Usuario usuario) {
		super();
		this.id = id;
		this.nome = nome;
		this.statusSistema = statusSistema;
		this.dataAtualizacao = dataAtualizacao;
		this.usuario = usuario;
	}

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

	public Boolean getstatusSistema() {
		return statusSistema;
	}

	public void setstatusSistema(Boolean statusSistema) {
		this.statusSistema = statusSistema;
	}

	public Instant getdataAtualizacao() {
		return dataAtualizacao;
	}

	public void setdataAtualizacao(Instant dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	public Usuario getUsuario() {
		return usuario;
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
		Sistema other = (Sistema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
