package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.jetro.util.Util;

public class ConverterCPF implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Util.removerFormatacao(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String cpf = (String) value;
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}

}
