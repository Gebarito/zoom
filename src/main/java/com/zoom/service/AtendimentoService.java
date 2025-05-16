package com.zoom.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

import com.zoom.dao.AtendimentoDAO;
import com.zoom.modelo.Atendimento;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.StatusAtendimento;
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
	private AtendimentoDAO AtendimentoDAO;
	@Inject
	private UsuarioService usuarioService;


	//TODO parametro so o atendimento. Atribuir os dados no bean.
	public Atendimento salvar(Atendimento atendimento, Unidade unidade, Usuario tecnicoAgendamento, LocalDateTime dataAgendamento) throws NegocioException {

		// TODO retirar
		/*
		Atendimento Atendimento;
		
		Atendimento = new Atendimento();
		Atendimento.setNome(atendimento.getNome());
		Atendimento.setTelefone(atendimento.getTelefone());
		Atendimento.setDescricao(atendimento.getDescricao());
		Atendimento.setDataAgendamento(dataAgendamento);
		Atendimento.setStatusAtendimento(StatusAtendimento.AGENDADO);
		Atendimento.setUnidade(unidade);
		Atendimento.setTecnicoAtendimento(atendimento.getTecnicoAtendimento());
		Atendimento.setTecnicoAgendamento(tecnicoAgendamento);
		
		if(atendimento.getTecnicoAtendimento() != null) {
			Atendimento.setTecnicoAtendimento(atendimento.getTecnicoAtendimento());
			log.info("event.getData() com tecnico = " + (atendimento.getTecnicoAtendimento().getNome())); 
		}else {
			Atendimento.setTecnicoAtendimento(null);
			log.info("event.getData() sem tecnico = "); 
		}
		*/

		return this.AtendimentoDAO.merge(atendimento);
	}

	//TODO idem ao salvar
	public void atualizar(Atendimento atendimento, Usuario tecnicoAgendamento, LocalDateTime dataAgendamento) throws NegocioException {

		Atendimento Atendimento = AtendimentoDAO.buscarPeloCodigo(atendimento.getCodigo());

		if(atendimento.getTecnicoAtendimento() != null) {

			Atendimento.setTecnicoAtendimento(Atendimento.getTecnicoAtendimento());

			log.info("event.getData() tecnico = " + (atendimento.getTecnicoAtendimento().getNome())); 
		}
		else {
			if(atendimento.getTecnicoAtendimento() != null) {
				throw new NegocioException("Não é permitido deixar sem um Assistente Social!");
			}
			Atendimento.setTecnicoAtendimento( null );			
		}		
		Atendimento.setNome(atendimento.getNome());
		Atendimento.setTelefone(atendimento.getTelefone());
		Atendimento.setDescricao(atendimento.getDescricao());
		Atendimento.setDataAgendamento(dataAgendamento);
		Atendimento.setStatusAtendimento(StatusAtendimento.AGENDADO);
		Atendimento.setTecnicoAgendamento(tecnicoAgendamento);

		//log.info("Atendimento recuperado " + calendario.getCodigo() + " end " + calendario.getEndDate()); 
		this.AtendimentoDAO.merge(Atendimento);
	}

	public void completarATendimento(Atendimento atendimento, LocalDateTime dataAtendido) throws NegocioException {

		//log.info("evento atz service " + (atendimento.getCodigo());

		Atendimento Atendimento = AtendimentoDAO.buscarPeloCodigo(atendimento.getCodigo());
		Atendimento.setDataAtendimento(dataAtendido);
		Atendimento.setStatusAtendimento(StatusAtendimento.ATENDIDO);

		//log.info("Atendimento recuperado " + calendario.getCodigo() + " end " + calendario.getEndDate()); 
		this.AtendimentoDAO.merge(Atendimento);
	}

	public void excluir(Atendimento atendimento) throws NegocioException {
		Atendimento Atendimento = AtendimentoDAO.buscarPeloCodigo(atendimento.getCodigo());
		AtendimentoDAO.excluir(Atendimento);
	}

	public Atendimento buscarPeloCodigo(Long codigo) {
		return AtendimentoDAO.buscarPeloCodigo(codigo);
	}	

	public List<Atendimento> buscarTodosAgendados(Unidade unidade) throws ParseException {
		return AtendimentoDAO.buscarTodosAgendados(unidade);
	}


	public AtendimentoDAO getCalendarioDAO() {
		return AtendimentoDAO;
	}
	
	public List<Usuario> buscarUsuarios(Unidade unidade) {		
		return usuarioService.buscarUsuarios(unidade);
	}
}