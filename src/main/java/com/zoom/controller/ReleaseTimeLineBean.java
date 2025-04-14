package com.zoom.controller;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.zoom.util.MessageUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author murakamiadmin
 *
 */

@Getter
@Setter
@Named
@ViewScoped
public class ReleaseTimeLineBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TimelineModel<String, ?> model;

    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
    
    @PostConstruct
    protected void initialize() {
        model = new TimelineModel<>();
        model.add(TimelineEvent.<String>builder().data("v0.2.0")
        		.startDate(LocalDate.of(2025, 04, 14)).build());
        model.add(TimelineEvent.<String>builder().data("v0.1.0")
        		.startDate(LocalDate.of(2025, 03, 20)).build());
       
    }

    /* LAST VERSION */
    private String lastVersion = "v0.2.0";
    /* LAST VERSION */
    
    public void onSelect(TimelineSelectEvent<String> e) {
        TimelineEvent<String> timelineEvent = e.getTimelineEvent();
        String versao = timelineEvent.getData();
        
        switch(versao) {
        case "v0.2.0": 
	    	MessageUtil.sucesso("14/abr/2025");
	    	MessageUtil.sucesso("Incluidos Cadastros e Login.");	    	
	    	break;
        case "v0.1.0": 
	    	MessageUtil.sucesso("20/mar/2025");
	    	MessageUtil.sucesso("Início do projeto Zoom.");	    	
	    	break;
        
	    default:
	    	MessageUtil.sucesso("Versão desconhecida");
	        break;
        } 
    }
}
