package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.LiderancaEC;
import br.com.jetro.modelo.membresia.Lideranca;

@FacesConverter(forClass=Lideranca.class)
public class LiderancaConverter implements Converter{

	@Inject
	private LiderancaEC liderancaEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Lideranca lideranca = null;
		
		if(value != null && !"".equals(value)){
			lideranca = liderancaEC.bucarPorId(Long.parseLong(value));
		}

		return lideranca;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Lideranca) value).getId() != null){
			retorno = ((Lideranca) value).getId().toString();
		}
		return retorno;

	}

}
