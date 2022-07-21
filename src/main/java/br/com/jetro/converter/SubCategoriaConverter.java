package br.com.jetro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.jetro.entitycontroler.financas.SubCategoriaEC;
import br.com.jetro.modelo.financas.SubCategoria;

@FacesConverter(forClass=SubCategoria.class)
public class SubCategoriaConverter implements Converter{

	@Inject
	private SubCategoriaEC subCategoriaEC;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		SubCategoria subCategoria = null;

		if(value != null && !"".equals(value)){

			subCategoria = subCategoriaEC.bucarPorId(Long.valueOf(value));
		}
		return subCategoria;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String retorno = null;

		if(value != null && ((SubCategoria) value).getId() != null){
			retorno = ((SubCategoria) value).getId().toString();
		}
		return retorno;

	}

}
