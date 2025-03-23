package com.zoom.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.Role;
import com.zoom.modelo.enums.Status;
import com.zoom.service.UnidadeService;
import com.zoom.service.UsuarioService;
import com.zoom.util.MessageUtil;
import com.zoom.util.NegocioException;

import jakarta.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterUsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Unidade unidade;
	private List<Role> roles;
	private List<Status> status;
	private List<Usuario> usuarios = new ArrayList<>();
	private List<Unidade> unidades;
	
	
	@Inject
	private LoginBean loginBean;	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private UnidadeService unidadeService;
	
	@PostConstruct
	public void inicializar() {
		log.info("init pesquisa");
		this.usuarios = usuarioService.buscarTodos();
		this.unidades = unidadeService.buscarTodos();
		limpar();
	}
	
	public void salvar() {
		
		try {	
			
			log.info("salvando = " + usuario.getUnidade());
			Usuario usu = usuarioService.salvar(usuario);			
			this.usuarios = usuarioService.buscarTodos();				
			MessageUtil.sucesso("Usuario salvo com sucesso!");
			log.info("usuario salvo = " + usu.getNome());
			
			this.limpar();
			
		} catch (PersistenceException e) {
			MessageUtil.erro("E-mail já cadastrado! Tente outro. O sistema não permite e-mails repetidos.");
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro("Erro desconhecido. Contatar o administrador"); 
		}		
	}

	public void limpar() {
		this.usuario = new Usuario();
		this.usuario.setUnidade(unidade);
		this.usuario.setStatus(Status.ATIVO);
	}	
	
	public void excluir() {

		try {
			log.info("excluindo...");
			usuarioService.excluir(usuario);			
			this.usuarios = usuarioService.buscarTodos();			
			MessageUtil.sucesso("Usuario " + usuario.getNome() + " excluído com sucesso.");
			log.info("Usuario excluido = " + usuario.getNome());
			
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
