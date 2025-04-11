package com.zoom.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.primefaces.model.ResponsiveOption;

import com.zoom.modelo.to.LogoTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author Edson Murakami
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<LogoTO> products = new ArrayList<>();;

	    private List<ResponsiveOption> responsiveOptions;

	   
	    @PostConstruct
	    public void init() {
	    	log.info("HomeBean inicializando...");
	        products = getProducts();
	        responsiveOptions = new ArrayList<>();
	        responsiveOptions.add(new ResponsiveOption("300px", 2, 2));
	        responsiveOptions.add(new ResponsiveOption("300px", 1, 1));
	    }    
	    

	    public List<LogoTO> getProducts() {
	    	products.add(new LogoTO("Ifsp", "IFSP_Salto_Transparente.png"));
	    	products.add(new LogoTO("Zoom", "logo-zoom.png"));  	
	    	
	        return products;
	    }
	    
	    public String entrar() {
	    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			
			try {
				ec.redirect(ec.getRequestContextPath() + "/restricted/home/ZoomHome.xhtml");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "/restricted/home/ZoomHome.xhtml";
	    }

}
