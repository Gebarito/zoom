package com.zoom.controller.prontuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.Atendido;
import com.zoom.modelo.Pessoa;
import com.zoom.util.MessageUtil;

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
@Named(value = "manterProntuarioBean")
@ViewScoped
public class ManterProntuarioBean extends AbrirDialogoPessoa implements Serializable {

	private static final long serialVersionUID = 1769116747361287180L;

	private Atendido atendido;
	private List<Pessoa> pessoas = new ArrayList<>();

	@Inject
	private LoginBean loginBean;
	@Inject
	private ResponsaveisBean responsaveisBean;

	@PostConstruct
	public void inicializar() {
		log.info("ManterProntuarioBean");
	}

	public void abrirDialogo() {
		abrirDialogoAtendido();
	}

	public void selecionarAtendido(SelectEvent<?> event) {

		this.atendido = (Atendido) event.getObject();

		this.responsaveisBean.setAtendido(atendido);

		log.info("ManterProntuarioBean atendido: " + atendido.getNome());
		MessageUtil.sucesso("Atendido selecionado: " + atendido.getNome());
	}

	public boolean isAtendidoSelecionado() {
		return atendido != null && atendido.getCodigo() != null;
	}

	
}