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

import br.com.jetro.assistentes.LancamentoFiltro;
import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.negocio.financas.CategoriaAS;
import br.com.jetro.negocio.financas.LancamentoAS;
import br.com.jetro.negocio.financas.SubCategoriaAS;


@Named
@ViewScoped
public class ConsultaLancamentosBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5950203451384353356L;
	
	@Inject
	private LancamentoAS lancamentoAS;
	
	@Inject
	private CategoriaAS categoriaAS;
	
	@Inject
	private SubCategoriaAS subCategoriaAS;
	
	private List<Lancamento> lancamentos;
	
	private Lancamento LancamentoSelecionado;
	
	private LancamentoFiltro filtro = new LancamentoFiltro();
	
	private List<Categoria> listaCategorias;
	
	private List<SubCategoria> listaSubCategorias;

	private boolean vizualizaDialogExclusao;
	
	
	public void prepararConsulta(){
		
		listaCategorias = categoriaAS.listar();
		Collections.sort(listaCategorias);
		
	}
	
	public void consultar(){
		
		this.lancamentos = lancamentoAS.listarPorFiltro(filtro);
		
	}
	
	public void limpar(){
		prepararConsulta();
		this.filtro = new LancamentoFiltro();
		this.lancamentos = new ArrayList<Lancamento>();
	}

	
	public void excluir(){
		lancamentoAS.excluir(this.LancamentoSelecionado);
		consultar();
		vizualizaDialogExclusao = false;
		
		adicionarMensagem(ConstantesMensagem.MN004, FacesMessage.SEVERITY_INFO);
	}
	
	public void atualizarListaSubCategoria(AjaxBehaviorEvent event){
		if(this.filtro.getCategoria() != null){
			listaSubCategorias = subCategoriaAS.listarPorCategoria(this.filtro.getCategoria().getId());
			Collections.sort(listaSubCategorias);
		}else{
			listaSubCategorias.clear();
		}
	}
	
	public void inicializarExclusao(){
		vizualizaDialogExclusao = true;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public Lancamento getLancamentoSelecionado() {
		return LancamentoSelecionado;
	}

	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		LancamentoSelecionado = lancamentoSelecionado;
	}

	public LancamentoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(LancamentoFiltro filtro) {
		this.filtro = filtro;
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

	public boolean isVizualizaDialogExclusao() {
		return vizualizaDialogExclusao;
	}

	public void setVizualizaDialogExclusao(boolean vizualizaDialogExclusao) {
		this.vizualizaDialogExclusao = vizualizaDialogExclusao;
	}
}
