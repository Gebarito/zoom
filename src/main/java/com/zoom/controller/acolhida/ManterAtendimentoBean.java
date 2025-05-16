package com.zoom.controller.acolhida;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.Atendimento;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.modelo.enums.StatusAtendimento;
import com.zoom.service.AtendimentoService;
import com.zoom.util.DateUtils;
import com.zoom.util.MessageUtil;
import com.zoom.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author Timpone
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterAtendimentoBean implements Serializable {

	private static final long serialVersionUID = 6570142544541959632L;

	private ScheduleModel eventModel;
	private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
	private List<Atendimento> atendimentos = new ArrayList<>();
	private Unidade unidade; 
	private String telefone;
	private List<Usuario> tecnicos;
	private List<StatusAtendimento> statusAtendimento;

	private LocalDate currentDate;
    private LocalDate minDate;
    private LocalDate maxDate;
    private LocalTime minTime;
    private LocalTime maxTime;
    private LocalDateTime minDateTime;
    private LocalDateTime maxDateTime;

	@Inject
	private AtendimentoService atendimentoService;
	@Inject
	private LoginBean loginBean;


    @PostConstruct
    public void init() {

    	unidade = loginBean.getUsuario().getUnidade(); 
    	this.tecnicos = atendimentoService.buscarUsuarios(unidade);
    	this.statusAtendimento = Arrays.asList(StatusAtendimento.values());

    	this.currentDate = LocalDate.now(); //inicia o calendário no dia atual
        minDate = LocalDate.now().minusYears(1);
        maxDate = LocalDate.now().plusYears(1);

        minTime = LocalTime.of(8, 0, 0);
        maxTime = LocalTime.of(18, 0, 0);

        minDateTime = LocalDateTime.of(minDate, minTime);
        maxDateTime = LocalDateTime.of(maxDate, maxTime);

    	event = DefaultScheduleEvent.builder()
				.startDate(DateUtils.asLocalDateTime(new Date()))
				.endDate(DateUtils.asLocalDateTime(new Date()))
				.data(new Atendimento())
				.build();

    	setEvent(event);

    	carregarCalendario();    	
    }

    private void carregarCalendario() {

    	eventModel = new DefaultScheduleModel();

    	try {
    		atendimentos = atendimentoService.buscarTodosAgendados(unidade);

		} catch (ParseException e) {
			MessageUtil.erro("Problema com a data da consulta.");
			e.printStackTrace();
		}

    	for(Atendimento atend : atendimentos) {  
    		
    		log.info("Acolhida de: " + atend.getNome() + " - Status: " + atend.getStatusAtendimento()); 	
    		
    		if(atend.getTecnicoAtendimento() != null) {
    			event = DefaultScheduleEvent.builder()
    					.title(atend.getNome() + " (" + atend.getTecnicoAtendimento().getNome()  + ")")
    					.description(atend.getTecnicoAtendimento().getNome())
    					.startDate(atend.getDataAgendamento())
    					.endDate(atend.getDataAgendamento())
    					.data(atend)
    					.borderColor(atend.getStatusAtendimento().getCor())
    					.backgroundColor(atend.getStatusAtendimento().getCor())
    					.build();
    		}
    		else {
    			event = DefaultScheduleEvent.builder()
    					.title(atend.getNome())
    					.description("não atribuido")
    					.startDate(atend.getDataAgendamento())
    					.endDate(atend.getDataAgendamento())
    					.data(atend)
    					.borderColor(atend.getStatusAtendimento().getCor())
    					.backgroundColor(atend.getStatusAtendimento().getCor())
    					.build();
    		}    		
			eventModel.addEvent(event);  
    	}
    	log.info("Carregado Calendário: " + atendimentos.size());
    }

	public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {

		event = selectEvent.getObject();

		LocalDateTime selectedDate = (LocalDateTime) event.getStartDate();

		try {
			// pega só o nome da pessoa agendada
			String descricao = event.getDescription();
			((DefaultScheduleEvent<?>) event).setTitle(descricao);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			log.info("erro para limpar title " + ((Atendimento) event.getData()).getNome());
		}

		log.info("((Atendimento)event.getData()).getNome() " + ((Atendimento) event.getData()).getNome());

		// Atualiza currentDate para a data selecionada
		this.currentDate = selectedDate.toLocalDate();
	}

 	public void onDateSelect(SelectEvent<?> selectEvent) { 

 		LocalDateTime selectedDate = (LocalDateTime) selectEvent.getObject();
 		event = DefaultScheduleEvent.builder()
 				.title("")
				.startDate((LocalDateTime) selectEvent.getObject())
				.data(new Atendimento())
				.build();

 	    setEvent(event);

 	    // Atualiza currentDate para a data selecionada
 	    this.currentDate = selectedDate.toLocalDate();
 	}

	public void addEvent() {       

		try {			
			
	        if(event.getId() == null) {
	        	
	        	eventModel.addEvent(event);
	        }
	        else {	 
	        	eventModel.updateEvent(event);
	        }
	        
	        atendimentoService.salvar(converteEvent(event));
	        
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível salvar o agendamento : " + e.getMessage());
			e.printStackTrace();
		}

		carregarCalendario();

        event = new DefaultScheduleEvent<>();
    }
	
	private Atendimento converteEvent( ScheduleEvent<?> event) {
		
		log.info("event = " + event.toString());
		
		Atendimento atendimento = (Atendimento)event.getData();
		
		atendimento.setDataAgendamento(event.getStartDate());
		atendimento.setStatusAtendimento(StatusAtendimento.AGENDADO);
		atendimento.setTecnicoAgendamento(loginBean.getUsuario());
		atendimento.setUnidade(unidade);
    	
    	log.info("atendimento = " + atendimento.toString());
    	
		return atendimento;
	}

	public void deleteEvent() {  

		try {
			
			Atendimento atendimento = (Atendimento)event.getData();
			atendimentoService.excluir(atendimento);
			eventModel.deleteEvent(event);
			
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível excluir o agendamento " + event.getId());
			e.printStackTrace();
		}
		log.info("Agendamento excluído. " + event.getData());  
    }

	public void completeEvent() {
		try {

	        if(event.getId() != null) {
	        	
	        	eventModel.updateEvent(event);
	        	
	        	Atendimento atendimento = (Atendimento)event.getData();	        	
	        	atendimento.setDataAtendimento(DateUtils.asLocalDateTime(new Date()));
	        	atendimento.setStatusAtendimento(StatusAtendimento.ATENDIDO);

	        	atendimentoService.salvar(atendimento);
	        	
	        }			
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível realizar o atendimento: " + e.getMessage());
			e.printStackTrace();
		}

		carregarCalendario();

        event = new DefaultScheduleEvent<>();
	}
}