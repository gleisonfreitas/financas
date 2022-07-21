package br.com.jetro.beans.financas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.financas.CategoriaEC;
import br.com.jetro.entitycontroler.financas.SubCategoriaEC;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.negocio.financas.LancamentoAS;

@Named
@ViewScoped
public class CadastroLancamentosBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2019292662387702633L;
	
	@Inject
	private LancamentoAS lancamentoAS;
	
	@Inject
	private CategoriaEC categoriaEC;
	
	@Inject
	private SubCategoriaEC subCategoriaEC;
	
	private Lancamento lancamento;
	
	private Categoria categoria;
	
	private List<Categoria> categorias;
	
	private List<SubCategoria> subCategorias;
	
	public void prepararCadastro(){
		
		categorias = categoriaEC.listar();
		Collections.sort(categorias);
		
		if(lancamento == null){
			lancamento = new Lancamento();
			subCategorias = new ArrayList<SubCategoria>();
		}else if(lancamento.getSubCategoria() != null){
			categoria = lancamento.getSubCategoria().getCategoria();
			if(categoria != null){
				subCategorias = subCategoriaEC.listarPorCategoria(categoria.getId());
			}
		}
			
	}
	
	public void salvar(){
		
		try {
			
			lancamentoAS.salvar(lancamento);
			
			this.lancamento = new Lancamento();
			
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			
		} catch (NegocioException e) {
			
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	
	public void atualizarListaSubCategoria(AjaxBehaviorEvent event){
		if(categoria != null){
			this.subCategorias = subCategoriaEC.listarPorCategoria(categoria.getId());
			Collections.sort(subCategorias);
		}else{
			subCategorias = new ArrayList<SubCategoria>();
		}				
	}
	
	public List<String> pesquisarDescricoes(String descricao){
		
		return lancamentoAS.descricoesQueContem(descricao);
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

}
