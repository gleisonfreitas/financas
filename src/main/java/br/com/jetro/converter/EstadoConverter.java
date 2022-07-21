package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.EstadoEC;
import br.com.jetro.modelo.membresia.Estado;

@FacesConverter(forClass=Estado.class)
public class EstadoConverter implements Converter{

	@Inject
	private EstadoEC estadoEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Estado estado = null;
		
		if(value != null && !"".equals(value)){
			estado = estadoEC.bucarPorId(Long.parseLong(value));
		}

		return estado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Estado) value).getId() != null){
			retorno = ((Estado) value).getId().toString();
		}
		return retorno;

	}

}
