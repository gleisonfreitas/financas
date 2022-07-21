package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.MembroEC;
import br.com.jetro.modelo.membresia.Membro;

@FacesConverter(forClass=Membro.class)
public class MembroConverter implements Converter{
	
	
	@Inject
	private MembroEC membroEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Membro membro = null;
		
		if(value != null && !value.trim().isEmpty()){
			membro = membroEC.bucarPorId(Long.valueOf(value)); 
		}
		return membro;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		String retorno = null;
		if(value != null){
			Membro membro = (Membro) value;
			
			retorno = membro.getId() != null ? membro.getId().toString() : null;
		}
		
		return retorno;
	}

}
