package com.zoom.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

import com.zoom.modelo.Unidade;
import com.zoom.util.NegocioException;
import com.zoom.util.jpa.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class UnidadeDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;	
	
	@Transactional
	public Unidade salvar(Unidade unidade) throws NegocioException {
		try {
			log.info("unidade nome dao = " + unidade.getNome());
			return manager.merge(unidade);
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
	
	@Transactional
	public void excluir(Unidade unidade) throws NegocioException {
		unidade = buscarPeloCodigo(unidade.getCodigo());
		try {
			manager.remove(unidade);
			manager.flush();
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
	
	
	public Unidade buscarPeloCodigo(Long codigo) {
		return manager.find(Unidade.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Unidade> buscarTodos() {
		return manager.createNamedQuery("Unidade.buscarTodos")
				.getResultList();
	}	
	

	
}