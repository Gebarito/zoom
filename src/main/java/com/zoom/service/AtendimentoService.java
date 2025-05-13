package com.zoom.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.ScheduleEvent;

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



	public Atendimento salvar(ScheduleEvent<?> event, Unidade unidade, Usuario tecnicoAgendamento) throws NegocioException {

		Atendimento Atendimento;

		if(((Atendimento)event.getData()).getTecnicoAtendimento() != null) {
			Atendimento = new Atendimento(event.getTitle(),
					((Atendimento)event.getData()).getTelefone(),
					((Atendimento)event.getData()).getDescricao(),
					event.getStartDate(),
					StatusAtendimento.AGENDADO, 
					unidade,
					((Atendimento)event.getData()).getTecnicoAtendimento());
			log.info("event.getData() COM tecnico = " + ((Atendimento)event.getData()).getTecnicoAtendimento().getNome()); 
		}
		else {
			Atendimento = new Atendimento(event.getTitle(),
					((Atendimento)event.getData()).getTelefone(),
					((Atendimento)event.getData()).getDescricao(),
					event.getStartDate(),
					StatusAtendimento.AGENDADO,
					unidade,
					null);
			log.info("event.getData() SEM tecnico = ");
		}
		Atendimento.setTecnicoAgendamento(tecnicoAgendamento);

		return this.AtendimentoDAO.merge(Atendimento);
	}

	public void atualizar(ScheduleEvent<?> event) throws NegocioException {

		//log.info("evento atz service " + ((Atendimento)event.getData()).getCodigo());

		Atendimento Atendimento = AtendimentoDAO.buscarPeloCodigo( ((Atendimento)event.getData()).getCodigo() );

		if(((Atendimento)event.getData()).getTecnicoAtendimento() != null) {


			Atendimento.setTecnicoAtendimento( ((Atendimento)event.getData()).getTecnicoAtendimento() );

			log.info("event.getData() tecnico = " + ((Atendimento)event.getData()).getTecnicoAtendimento().getNome()); 
		}
		else {
			if(Atendimento.getTecnicoAtendimento() != null) {
				throw new NegocioException("Não é permitido deixar sem um Assistente Social!");
			}
			Atendimento.setTecnicoAtendimento( null );			
		}		
		Atendimento.setNome(event.getTitle());
		Atendimento.setStatusAtendimento(StatusAtendimento.AGENDADO);
		Atendimento.setDataAgendamento(event.getStartDate());

		//log.info("Atendimento recuperado " + calendario.getCodigo() + " end " + calendario.getEndDate()); 
		this.AtendimentoDAO.merge(Atendimento);
	}

	public void completarATendimento(ScheduleEvent<?> event) throws NegocioException {

		//log.info("evento atz service " + ((Atendimento)event.getData()).getCodigo());

		Atendimento Atendimento = AtendimentoDAO.buscarPeloCodigo( ((Atendimento)event.getData()).getCodigo() );
		Atendimento.setDataAtendimento(event.getEndDate());
		Atendimento.setStatusAtendimento(StatusAtendimento.ATENDIDO);

		//log.info("Atendimento recuperado " + calendario.getCodigo() + " end " + calendario.getEndDate()); 
		this.AtendimentoDAO.merge(Atendimento);
	}

	public void excluir(ScheduleEvent<?> event) throws NegocioException {
		Atendimento calendario = AtendimentoDAO.buscarPeloCodigo( ((Atendimento)event.getData()).getCodigo());
		AtendimentoDAO.excluir(calendario);		
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