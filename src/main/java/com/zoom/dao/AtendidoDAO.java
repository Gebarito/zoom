package com.zoom.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.zoom.modelo.Atendido;
import com.zoom.modelo.Unidade;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class AtendidoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;	
	
	
	
	
	/*
	 * Buscas	 
	 */
	
	
	public Unidade buscarPeloCodigo(Long codigo) {
		return manager.find(Unidade.class, codigo);
	}
		
	@SuppressWarnings("unchecked")
	public List<Atendido> buscarPorNome(String nome, Unidade unidade) {		
		
		//TODO todas as buscas devem ser por unidade do usuario logado, incluse esta pesquisa
		return manager.createQuery("select a from Atendido a "
				+ "where a.nome LIKE :nome")
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
}