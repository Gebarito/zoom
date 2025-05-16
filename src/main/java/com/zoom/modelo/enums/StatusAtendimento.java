package com.zoom.modelo.enums;

/**
 * @author Timpone
 *
 */
public enum StatusAtendimento {

	AGENDADO("AGENDADO","orange"),
	ATENDIDO("ATENDIDO","green");

	private final String descricao;
	private final String cor;


	StatusAtendimento(String descricao, String cor) {
	        this.descricao = descricao;
	        this.cor = cor;
	}

	public String getDescricao() {
	        return descricao;
	}

	public String getCor() {
		return cor;
	}


}