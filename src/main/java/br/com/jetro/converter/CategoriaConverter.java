package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.financas.CategoriaEC;
import br.com.jetro.modelo.financas.Categoria;

@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter{

	@Inject
	private CategoriaEC categoriaEC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Categoria categoria = null;
		
		if(value != null && !"".equals(value)){
			categoria = categoriaEC.bucarPorId(Long.parseLong(value));
		}

		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String retorno = null;
		if(value != null && ((Categoria) value).getId() != null){
			retorno = ((Categoria) value).getId().toString();
		}
		return retorno;

	}

}
