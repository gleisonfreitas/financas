package br.com.jetro.enums;

public enum TipoLancamento {
	
	CREDITO("Crédito"),
	DEBITO("Débito");
	
	private String nome;

	private TipoLancamento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
