package com.zoom.service;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.inject.Inject;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import com.zoom.dao.UsuarioDAO;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.Role;
import com.zoom.modelo.enums.Status;
import com.zoom.util.NegocioException;

import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

/**
 * @author murakamiadmin
 *
 */
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(UsuarioService.class);
	
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private UnidadeService unidadeService;
	
	public Usuario salvar(Usuario usuario) throws NegocioException, SQLIntegrityConstraintViolationException {
				
		validaDados(usuario);
		
		if(usuario.getSenha() == null || usuario.getSenha().equals(""))
			usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));		
					
		return this.usuarioDAO.salvar(usuario);
	}
	
	private void validaDados(Usuario usuario) throws NegocioException {
		
		if (usuario.getRole() == null) 
			throw new NegocioException("O papel (role) é obrigatório.");
		if (usuario.getStatus() == null) 
			throw new NegocioException("O status é obrigatório.");	
		
	}		


	public void excluir(Usuario usuario) throws NegocioException {
		usuario.setStatus(Status.INATIVO);
		usuarioDAO.excluir(usuario);
		
	}
	
	public void reset(Usuario usuario) throws NegocioException, SQLIntegrityConstraintViolationException {			
		
		usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));		
		this.usuarioDAO.salvar(usuario);
	}	

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}


	public Usuario buscarPeloEmail(String email) throws NoResultException {
		return usuarioDAO.buscarPeloEmail(email);
	}

	public void trocarSenha(Usuario usuario, String senhaAntiga) throws NegocioException, PersistenceException {
					
		Usuario u = buscarPeloEmail(usuario.getEmail());
		
		if(u != null) {
			if(!BCrypt.checkpw(senhaAntiga, u.getSenha()))
				throw new NegocioException("Senha não confere! Digite a senha antiga corretamente.");
			else {
				usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
				this.usuarioDAO.salvar(usuario);
			}				
		}
			
	}
	
	public void alterarPerfil(Usuario usuario) throws NegocioException, PersistenceException {		
		this.usuarioDAO.salvar(usuario);
	}

	public List<Usuario> buscarTodos() {
		return usuarioDAO.buscarTodos();
	}

	public List<Usuario> buscarTecnicos(Unidade unidade) {		
		return usuarioDAO.buscarTecnicos(unidade);
	}
	public List<Usuario> buscarTecnicosRole(Role role, Unidade unidade) {
		return usuarioDAO.buscarTecnicosRole(role, unidade);
	}
	public List<Usuario> buscarUsuarios(Unidade unidade) {		
		return usuarioDAO.buscarUsuarios(unidade);
	}


	public List<Unidade> buscarUnidades() {	
		log.info("Primeiro acesso a banco... buscar unidades");
		
		return unidadeService.buscarTodos();
	}
	
	
}
