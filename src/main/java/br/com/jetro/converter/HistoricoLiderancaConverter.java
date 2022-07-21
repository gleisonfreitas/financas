package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.membresia.HistoricoLiderancaEC;
import br.com.jetro.modelo.membresia.HistoricoLideranca;

@FacesConverter(forClass=HistoricoLideranca.class)
public class HistoricoLiderancaConverter implements Converter{

	@Inject
	private HistoricoLiderancaEC historicoLiderancaEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		HistoricoLideranca historicoLideranca = null;
		
		if(value != null && !"".equals(value)){
			historicoLideranca = historicoLiderancaEC.bucarPorId(Long.parseLong(value));
		}

		return historicoLideranca;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((HistoricoLideranca) value).getId() != null){
			retorno = ((HistoricoLideranca) value).getId().toString();
		}
		return retorno;

	}

}
