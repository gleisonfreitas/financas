package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.financas.LancamentoEC;
import br.com.jetro.modelo.financas.Lancamento;

@FacesConverter(forClass=Lancamento.class)
public class LancamentoConverter implements Converter{
	
	@Inject
	private LancamentoEC lancamentoEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Lancamento lancamento = null;
		
		if(value != null && !value.trim().isEmpty()){
			lancamento = lancamentoEC.bucarPorId(Long.valueOf(value)); 
		}
		return lancamento;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		String retorno = null;
		if(value != null){
			Lancamento lancamento = (Lancamento) value;
			
			retorno = lancamento.getId() != null ? lancamento.getId().toString() : null;
		}
		
		return retorno;
	}
}
