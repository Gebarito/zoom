package com.zoom.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.zoom.modelo.Usuario;
import com.zoom.service.UsuarioService;
import com.zoom.util.MessageUtil;
import com.zoom.util.NegocioException;

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
@Named
@ViewScoped
public class AlterarPerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String senhaAntiga;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private LoginBean usuarioLogado;
	
	@PostConstruct
	public void inicializar() {
		
		log.info("Alterar Perfil " + usuarioLogado.getUsuario());
		this.usuario = usuarioLogado.getUsuario();		
	}
	
	public void salvarSenha() {
		try {
			this.usuarioService.trocarSenha(usuario, senhaAntiga);
			MessageUtil.sucesso("Senha alterada com sucesso! Saia do sistema e faça login novamente.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		} catch (Exception le) {
			le.printStackTrace();
			MessageUtil.erro(le.getMessage());
		}

	}
	
	public void salvarPerfil() {
		try {
			
			log.info("salvarPerfil()...");			
			
			this.usuarioService.alterarPerfil(usuario);
			MessageUtil.sucesso("Perfil alterado com sucesso! Saia do sistema e faça login novamente.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		} catch (Exception le) {
			le.printStackTrace();
			MessageUtil.erro(le.getMessage());
		}

	}

}
