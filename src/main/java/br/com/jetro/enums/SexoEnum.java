package br.com.jetro.enums;

public enum SexoEnum {
	
	MASCULINO("Masculino"),
	FEMININO("Feminino");
	
	private String nome;

	private SexoEnum(String nome){
		this.nome = nome;
		
	}

	public String getNome() {
		return nome;
	}

}
