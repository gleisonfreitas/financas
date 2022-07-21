package br.com.jetro.enums;

public enum NacionalidadeEnum {
	
	BRASILEIRO("Brasileiro(a)"),
	OUTROS("Outros");
	
	private String nome;

	private NacionalidadeEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
