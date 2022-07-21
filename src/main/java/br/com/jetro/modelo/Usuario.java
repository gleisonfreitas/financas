package br.com.jetro.modelo;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Named
@SessionScoped
@Entity
@Table(name="usuario")
@NamedQueries(
		@NamedQuery(name="login", query="SELECT u FROM Usuario u WHERE u.senha = :senha AND u.nome = :nome"))
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -569005040268671901L;
	
	@Id
	private Long id;

	@Column(name="nome")
	private String nome;
	
	@Column(name="senha")
	private String senha;
	
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Boolean isLogado() {
		return this.nome != null;
	}
}
