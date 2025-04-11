package com.zoom.controller;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.model.menu.MenuModel;

/**
 * @author murakamiadmin
 *
 */
@Named
@ViewScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;	
		
	public MenuModel getMenu() {
		return loginBean.getMenu();
	}

	public long getTempo() {
		return loginBean.getTempoRestante();
	}
}
