package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.jetro.util.Util;

public class ConverterTelefoneCelular implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Util.removerFormatacao(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String cpf = Util.removerFormatacao((String)value);
		return cpf.replaceAll("(\\d{2})(\\d{5})", "($1) $2-");
	}

}
