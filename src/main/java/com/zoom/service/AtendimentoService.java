package com.zoom.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

import com.zoom.dao.AtendimentoDAO;
import com.zoom.modelo.Atendimento;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author Timpone
 *
 */
@Log4j

public class AtendimentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AtendimentoDAO atendimentoDAO;
	@Inject
	private UsuarioService usuarioService;
	

	public Atendimento salvar(Atendimento atendimento) throws NegocioException {
		
		return this.atendimentoDAO.salvar(atendimento);
	}	

	public void excluir(Atendimento atendimento) throws NegocioException {
		Atendimento Atendimento = atendimentoDAO.buscarPeloCodigo(atendimento.getCodigo());
		atendimentoDAO.excluir(Atendimento);
	}
	
	/*
	 * Consultas
	 */

	public Atendimento buscarPeloCodigo(Long codigo) {
		return atendimentoDAO.buscarPeloCodigo(codigo);
	}	

	public List<Atendimento> buscarTodosAgendados(Unidade unidade) throws ParseException {
		return atendimentoDAO.buscarTodosAgendados(unidade);
	}

	public List<Usuario> buscarUsuarios(Unidade unidade) {		
		return usuarioService.buscarUsuarios(unidade);
	}
}