package com.zoom.controller;

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
	private List<Atendimento> Atendimento = new ArrayList<>();
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
	private AtendimentoService AtendimentoService;
	@Inject
	private LoginBean loginBean;


    @PostConstruct
    public void init() {

    	unidade = loginBean.getUsuario().getUnidade(); 
    	this.tecnicos = AtendimentoService.buscarUsuarios(unidade);
    	this.statusAtendimento = Arrays.asList(StatusAtendimento.values());

    	this.currentDate = LocalDate.now(); //inicia o calendário no dia atual
        minDate = LocalDate.now().minusYears(1);
        maxDate = LocalDate.now().plusYears(1);

        minTime = LocalTime.of(9, 0, 0);
        maxTime = LocalTime.of(17, 0, 0);

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
    		Atendimento = AtendimentoService.buscarTodosAgendados(unidade);

		} catch (ParseException e) {
			MessageUtil.erro("Problema com a data da consulta.");
			e.printStackTrace();
		}

    	for(Atendimento c : Atendimento) {  
    		log.info("Acolida de: " + c.getNome() + " - Status: " + c.getStatusAtendimento()); 	
    		if(c.getStatusAtendimento() == null) {
    			c.setStatusAtendimento(StatusAtendimento.AGENDADO);
    		}
    		if(c.getTecnicoAtendimento() != null) {
    			event = DefaultScheduleEvent.builder()
    					.title(c.getNome() + " " + c.getStatusAtendimento().getDescricao() + " para: " + c.getTecnicoAtendimento().getNome())
    					.description(c.getNome())
    					.startDate(c.getDataAgendamento())
    					.endDate(c.getDataAgendamento())
    					.data(c)
    					.borderColor(c.getStatusAtendimento().getCor())
    					.backgroundColor(c.getStatusAtendimento().getCor())
    					.build();
    		}
    		else {
    			event = DefaultScheduleEvent.builder()
    					.title(c.getNome() + " (" + c.getStatusAtendimento().getDescricao() + ")")
    					.description(c.getNome())
    					.startDate(c.getDataAgendamento())
    					.endDate(c.getDataAgendamento())
    					.data(c)
    					.borderColor(c.getStatusAtendimento().getCor())
    					.backgroundColor(c.getStatusAtendimento().getCor())
    					.build();
    		}    		
			eventModel.addEvent(event);  
    	}
    	log.info("Carregado Calendário: " + Atendimento.size());
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {

		event = selectEvent.getObject();

		LocalDateTime selectedDate = (LocalDateTime) event.getStartDate();

        log.debug("event " + getEvent()); 			
			try {
				// pega só o título, sem o nome da Atendente
				String descricao = event.getDescription(); 
				((DefaultScheduleEvent<?>) event).setTitle(descricao);
			}
			catch(ArrayIndexOutOfBoundsException e) {
				log.info("erro para limpar title " + ((Atendimento)event.getData()).getNome()); 
			}

			log.debug("((Atendimento)event.getData()).getNome() " + ((Atendimento)event.getData()).getNome()); 

        // Atualize currentDate para a data selecionada
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

 	    // Atualize currentDate para a data selecionada
 	    this.currentDate = selectedDate.toLocalDate();
 	}

	public void addEvent() {       

		try {

			//log.info("adicionando novo....... " + event.getId()); 
	        if(event.getId() == null) {
	        	//log.info("event id = null....... " + event.getId()); 
	        	eventModel.addEvent(event);
	        	//log.info("event adicionado no eventmodel....... ");
	    		
	        	Atendimento atendimento = (Atendimento)event.getData();
	        	
	        	//TODO atualizar os atributos de atendimento para minimizar codigo no service
	        	atendimento.setDataAgendamento(event.getStartDate());
	        	
	        	log.info("event.data = " + atendimento.toString());
	        	
	        	AtendimentoService.salvar(atendimento, unidade, loginBean.getUsuario(), event.getStartDate());
	            //log.info("evento adicionado " + event.getId() + ((Atendimento)event.getData()).getNome() + event.getStartDate()+ event.getEndDate()); 
	        }
	        else {	 
	        	//log.info("atualizando ....... " + event.getId());
	        	eventModel.updateEvent(event);
	        	//log.info("atualizado eventmodel....... " + event.getId());
	    		
	        	AtendimentoService.atualizar(((Atendimento)event.getData()), loginBean.getUsuario(), event.getStartDate());
	            //log.info("evento alterado " + event.getId() + ((Atendimento)event.getData()).getNome() + event.getStartDate()+ event.getEndDate());
	        }			
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível salvar o evento : " + e.getMessage());
			e.printStackTrace();
		}
		//log.info("recarregando calendario....... " + event.getId());
		carregarCalendario();
		//log.info("criando novo event....... " );
        event = new DefaultScheduleEvent<>();
        //log.info("criado novo event....... " + event.getId());
    }

	public void deleteEvent() {  

		try {
			AtendimentoService.excluir((Atendimento)event.getData());
			eventModel.deleteEvent(event);
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível excluir o evento " + event.getId());
			e.printStackTrace();
		}
		log.info("evento apagado " + event.getData());  
    }

	public void completeEvent() {
		try {
			//log.info("adicionando novo....... " + event.getId()); 
	        if(event.getId() == null) {

	        }
	        else {	 
	        	//log.info("atualizando ....... " + event.getId());
	        	eventModel.updateEvent(event);
	        	//log.info("atualizado eventmodel....... " + event.getId());

	        	AtendimentoService.completarATendimento((Atendimento)event.getData(), DateUtils.asLocalDateTime(new Date()));
	            log.info("evento alterado " + event.getId() + ((Atendimento)event.getData()).getNome() + event.getStartDate()+ event.getEndDate()); 
	        }			
		} catch (NegocioException e) {
			MessageUtil.erro("Não foi possível terminar o acolimento: " + e.getMessage());
			e.printStackTrace();
		}
		//log.info("recarregando calendario....... " + event.getId());
		carregarCalendario();
		//log.info("criando novo event....... " );
        event = new DefaultScheduleEvent<>();
        //log.info("criado novo event....... " + event.getId());
	}
}