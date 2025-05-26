package com.zoom.controller.prontuario;

import java.util.HashMap;
import java.util.Map;

import org.primefaces.PrimeFaces;

public abstract class AbrirDialogoPessoa {

	private static Map<String,Object> options = new HashMap<String, Object>();
	
	static {
		options.put("modal", true);
		options.put("width", 1000);
	    options.put("height", 600);
	    options.put("contentWidth", "100%");
	    options.put("contentHeight", "100%");
	    options.put("draggable", true);
	    options.put("responsive", true);
	    options.put("closeOnEscape", true);
	}

	
	/*
	 * Componente para abertura de dialogo para seleção de pessoas
	 */
	
	public void abrirDialogoAtendido() {

		PrimeFaces.current().dialog().openDynamic("/componentes/SelecionaAtendido", options, null);
    }
}
