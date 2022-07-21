package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.CidadeEC;
import br.com.jetro.modelo.membresia.Cidade;

@FacesConverter(forClass=Cidade.class)
public class CidadeConverter implements Converter{

	@Inject
	private CidadeEC cidadeEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Cidade cidade = null;
		
		if(value != null && !"".equals(value)){
			cidade = cidadeEC.bucarPorId(Long.parseLong(value));
		}

		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Cidade) value).getId() != null){
			retorno = ((Cidade) value).getId().toString();
		}
		return retorno;

	}

}
