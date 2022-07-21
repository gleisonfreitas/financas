package br.com.jetro.beans.financas;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.negocio.financas.CategoriaAS;
import br.com.jetro.negocio.financas.SubCategoriaAS;

@Named
@ViewScoped
public class CadastroSubCategoriasBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1171897173621555963L;
	

	@Inject
	private CategoriaAS categoriaAS;
	
	@Inject
	private SubCategoriaAS subCategoriaAS;
	
	private SubCategoria subCategoria;
	
	private List<Categoria> listaCategorias;
	
	private List<SubCategoria> listaSubCategorias;
			
	public void prepararCadastro(){
		
		listaCategorias = categoriaAS.listar();
		Collections.sort(listaCategorias);
		
		this.subCategoria = new SubCategoria();
		
	}
	
	public void salvarSubCategoria(){
		
		try {
			subCategoriaAS.salvar(this.subCategoria);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			prepararCadastro();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void atualizarListaSubCategoria(AjaxBehaviorEvent event){
		if(this.subCategoria != null && 
				this.subCategoria.getCategoria() != null &&
				this.subCategoria.getCategoria().getId() != null){
			listaSubCategorias = subCategoriaAS.listarPorCategoria(this.subCategoria.getCategoria().getId());
			Collections.sort(listaSubCategorias);
		}else{
			listaSubCategorias.clear();
		}
	}
	
	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<SubCategoria> getListaSubCategorias() {
		return listaSubCategorias;
	}

	public void setListaSubCategorias(List<SubCategoria> listaSubCategorias) {
		this.listaSubCategorias = listaSubCategorias;
	}
}
