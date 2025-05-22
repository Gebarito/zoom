package com.zoom.controller.prontuario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import com.zoom.modelo.Atendido;
import com.zoom.modelo.Pessoa;

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
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	
	@PostConstruct
	public void inicializar() {		
		log.info("ResponsaveisBean");
	}	
	
	public void limpar() {
		log.info("limpando...");
	}
	
	// verifica se há nomes iguais no sistema todo
	public void verificarDuplicidade() {

		//this.pessoas = responsaveisService.verificarNomeExistente(nome);

		if (pessoas.size() > 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cuidado",
					"Existem mais pessoas com o mesmo nome."));
		}
	}

}