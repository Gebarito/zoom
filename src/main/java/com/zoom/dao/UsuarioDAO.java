package com.zoom.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.Role;
import com.zoom.modelo.enums.Status;
import com.zoom.util.NegocioException;
import com.zoom.util.jpa.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

/**
 * @author murakamiadmin
 *
 */
public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws PersistenceException, NegocioException {
		try {
			return manager.merge(usuario);	
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}	
		
	@Transactional
	public void excluir(Usuario usuario) throws NegocioException {
			
		try {
			manager.merge(usuario);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}
	
	
	
	/*
	 * Buscas
	 */
	
	public Usuario buscarPeloCodigo(Long codigo) {
		return manager.find(Usuario.class, codigo);
	}
	
	public Usuario buscarPeloEmail(String email) throws NoResultException {
		return manager.createNamedQuery("Usuario.buscarPorEmail", Usuario.class)
				.setParameter("email", email)
				.getSingleResult();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		return manager.createNamedQuery("Usuario.buscarTodos").getResultList();
	}
		
	
	
	/* Buscas caso de uso ManterSCFV, RealizarAtendimento ind e col e ManterPAIF */
	
	public List<Usuario> buscarTecnicos(Unidade unidade) {
		return manager.createNamedQuery("Usuario.buscarTecnicos", Usuario.class)				
				.setParameter("unidade", unidade)
				.setParameter("status", Status.ATIVO)
				.getResultList();
	}
	public List<Usuario> buscarTecnicosRole(Role role, Unidade unidade) {
		return manager.createNamedQuery("Usuario.buscarTecnicosRole", Usuario.class)
				.setParameter("role", role)
				.setParameter("unidade", unidade)
				.setParameter("status", Status.ATIVO)
				.getResultList();
	}
	public List<Usuario> buscarUsuarios(Unidade unidade) {
		return manager.createNamedQuery("Usuario.buscarUsuarios", Usuario.class)				
				.setParameter("unidade", unidade)
				.setParameter("status", Status.ATIVO)
				.getResultList();
	}
	
	/* quantidades*/
	

	public Long encontrarQuantidadeDeUsuarios() {
		return manager.createQuery("select count(u) from Usuario u", Long.class)
				.getSingleResult();
	}
	public Long encontrarQuantidadeDeUsuarios(String nome) {
		return manager.createQuery("select count(u) from Usuario u where u.nome LIKE :nome ", Long.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getSingleResult();
	}
	public Long encontrarQuantidadeDeUsuarios(Unidade unidade) {
		return manager.createQuery("select count(u) from Usuario u where u.unidade = :unidade ", Long.class)
				.setParameter("unidade", unidade)
				.getSingleResult();
	}
	public Long encontrarQuantidadeDeUsuarios(Unidade unidade, String nome) {
		return manager.createQuery("select count(u) from Usuario u where u.unidade = :unidade "
				+ "and u.nome LIKE :nome ", Long.class)
				.setParameter("unidade", unidade)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getSingleResult();
	}
	public Long buscarTotalTecnicos() {
		return manager.createNamedQuery("Usuario.buscarTotalTecnicos", Long.class)
				.setParameter("status", Status.ATIVO)
				.getSingleResult();
	}
	public Long buscarTotalTecnicosUnid(Unidade unidade) {
		return manager.createNamedQuery("Usuario.buscarTotalTecnicosUnid", Long.class)
				.setParameter("unidade", unidade)
				.setParameter("status", Status.ATIVO)
				.getSingleResult();
	}	
	

	
}