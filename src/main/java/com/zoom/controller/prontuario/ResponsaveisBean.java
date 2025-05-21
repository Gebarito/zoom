package com.zoom.controller.prontuario;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import com.zoom.modelo.Atendido;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
@Getter
@Setter
@ViewScoped
public class ResponsaveisBean implements Serializable {

	private static final long serialVersionUID = 1769116747361287180L;

	private Atendido atendido;
	
	@PostConstruct
	public void inicializar() {		
		log.info("ResponsaveisBean");
	}	
	
	public void limpar() {
		log.info("limpando...");
	}
}