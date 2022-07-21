package br.com.jetro.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Configuracao")
public class ConfiguracaoGeral {
	
	@Id
	@Column(name="id", length=18, nullable=false)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Column(name="presidente", length=50, nullable=false)
	private String presidente;
	
	@NotNull
	@NotEmpty
	@Column(name="tesoureiro", length=50, nullable=false)
	private String tesoureiro;
	
	@Column(name="convencao", length=50, nullable=false)
	private String nomeConvencao;
	
	@Column(name="banco", length=50, nullable=false)
	private String nomeBanco;
	
	@Column(name="agencia", length=50, nullable=false)
	private String nomeAgencia;
	
	@Column(name="conta", length=50, nullable=false)
	private String numeroConta;

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public String getTesoureiro() {
		return tesoureiro;
	}

	public void setTesoureiro(String tesoureiro) {
		this.tesoureiro = tesoureiro;
	}

	public String getNomeConvencao() {
		return nomeConvencao;
	}

	public void setNomeConvencao(String nomeConvencao) {
		this.nomeConvencao = nomeConvencao;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	

}
