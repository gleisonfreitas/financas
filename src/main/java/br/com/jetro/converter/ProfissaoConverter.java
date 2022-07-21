package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.ProfissaoEC;
import br.com.jetro.modelo.membresia.Profissao;

@FacesConverter(forClass=Profissao.class)
public class ProfissaoConverter implements Converter{

	@Inject
	private ProfissaoEC profissaoEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Profissao profissao = null;
		
		if(value != null && !"".equals(value)){
			profissao = profissaoEC.bucarPorId(Long.parseLong(value));
		}

		return profissao;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Profissao) value).getId() != null){
			retorno = ((Profissao) value).getId().toString();
		}
		return retorno;

	}

}
