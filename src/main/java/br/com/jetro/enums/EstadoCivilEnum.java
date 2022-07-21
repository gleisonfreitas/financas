package br.com.jetro.enums;

public enum EstadoCivilEnum {
	
	SOLTEIRO("Solteiro"),
	CASADO("Casado"),
	DIVORCIADO("Divorciado"),
	UNIAO_ESTAVEL("União Estável"),
	VIUVO("Viúvo");
	
	
	private String nome;

	private EstadoCivilEnum(String nome){
		this.nome = nome;
		
	}

	public String getNome() {
		return nome;
	}

}
