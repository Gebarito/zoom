package com.zoom.componente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.Atendido;
import com.zoom.util.MessageUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class SelecionaAtendidoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String termoPesquisa;
	private String parametro = "nome";
	private List<Atendido> listaAtendidos = new ArrayList<>();	

	@Inject
	private LoginBean loginBean;
	
	
	public void pesquisar() {	
		
		//log.info("Termo no bean: "+termoPesquisa);	
		
		if(termoPesquisa != null && !termoPesquisa.equals("")) {
			
			if(getParametro().equals("nome")){
				
				//TODO buscar
				log.info("buscando por nome: " + getParametro());
				Atendido atendido = new Atendido();
				atendido.setCodigo(1l);
				atendido.setNome("fulano atendido");
				listaAtendidos.add(atendido);
			}
			
			if (listaAtendidos.isEmpty()) {
	            MessageUtil.alerta("Sua consulta não encontrou nenhum atendido nesta unidade.");
	        }
		}			
		else {
			MessageUtil.alerta("Digite um nome para a pesquisa.");
		}
	}	
	
	public void selecionarAtendido(Atendido atendido) {
		log.info("atendido selecionado: " + atendido.getCodigo() + "-" + atendido.getNome());
		PrimeFaces.current().dialog().closeDynamic(atendido);
	}

		
}