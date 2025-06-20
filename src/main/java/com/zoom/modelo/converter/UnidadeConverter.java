package com.zoom.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.zoom.dao.UnidadeDAO;
import com.zoom.modelo.Unidade;
import com.zoom.util.cdi.CDIServiceLocator;



/**
 * @author murakamiadmin
 *
 */
@FacesConverter(forClass=Unidade.class)
public class UnidadeConverter implements Converter<Object> {

	private UnidadeDAO unidadeDAO;
	
	public UnidadeConverter() {
		this.unidadeDAO = CDIServiceLocator.getBean(UnidadeDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Unidade retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.unidadeDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Unidade) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}