package com.zoom.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.zoom.dao.AtendidoDAO;
import com.zoom.modelo.Atendido;
import com.zoom.modelo.Unidade;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class SelecionaPessoaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AtendidoDAO atendidoDAO;



	public List<Atendido> buscarPorNome(String termoPesquisa, Unidade unidade) {
		return atendidoDAO.buscarPorNome(termoPesquisa, unidade);
	}

}