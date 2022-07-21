package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.FuncaoEC;
import br.com.jetro.modelo.membresia.Funcao;

@FacesConverter(forClass=Funcao.class)
public class FuncaoConverter implements Converter{

	@Inject
	private FuncaoEC funcaoEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Funcao funcao = null;
		
		if(value != null && !"".equals(value)){
			funcao = funcaoEC.bucarPorId(Long.parseLong(value));
		}

		return funcao;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Funcao) value).getId() != null){
			retorno = ((Funcao) value).getId().toString();
		}
		return retorno;

	}

}
