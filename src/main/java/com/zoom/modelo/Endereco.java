package com.zoom.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Embeddable
public class Endereco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "O endereco é obrigatório.")
	private String logradouro;
	private Long numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String municipio;
	@NotBlank(message = "A UF é obrigatória.")
	private String uf;
	private String referencia;
	private String telefoneContato;
	
	@Override
	public String toString() {
		return logradouro+numero+complemento+bairro+cep+municipio+uf+referencia+telefoneContato;
	}

}