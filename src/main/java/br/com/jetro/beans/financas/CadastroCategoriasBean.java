package br.com.jetro.beans.financas;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.enums.TipoLancamento;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.negocio.financas.CategoriaAS;

@Named
@ViewScoped
public class CadastroCategoriasBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1171897173621555963L;
	

	@Inject
	private CategoriaAS categoriaAS;
	
	private Categoria categoria;
	
	private List<Categoria> listaCategorias;
			
	public void prepararCadastro(){
		
		listaCategorias = categoriaAS.listar();
		Collections.sort(listaCategorias);
		
		this.categoria = new Categoria();
		
	}
	
	public void salvarCategoria(){
		
		try {
			categoriaAS.salvar(this.categoria);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public TipoLancamento[] getTipoLancamentos(){
		return TipoLancamento.values();
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	
	
}
