package com.zoom.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.zoom.dao.UnidadeDAO;
import com.zoom.modelo.Unidade;
import com.zoom.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class UnidadeService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UnidadeDAO unidadeDAO;



	public Unidade salvar(Unidade unidade) throws NegocioException {		
	
		log.info("unidade nome 2 = " + unidade.getNome());
		return this.unidadeDAO.salvar(unidade);
	}
	
	public Unidade buscarPeloCodigo(long codigo) {
		return unidadeDAO.buscarPeloCodigo(codigo);
	}
	

	public List<Unidade> buscarTodos() {
		return unidadeDAO.buscarTodos();
	}	
	
	public void excluir(Unidade unidade) throws NegocioException {
		unidadeDAO.excluir(unidade);
		
	}
	
	
	public List<Unidade> buscarUnidades() {	
		log.info("Primeiro acesso a banco... buscar unidades");
		
		return unidadeDAO.buscarTodos();
	}
	


	public UnidadeDAO getUnidadeDAO() {
		return unidadeDAO;
	}

}