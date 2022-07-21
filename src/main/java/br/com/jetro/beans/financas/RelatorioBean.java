package br.com.jetro.beans.financas;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import br.com.jetro.assistentes.RelatorioGeral;
import br.com.jetro.assistentes.RelatorioResumoMensal;
import br.com.jetro.assistentes.ResumoLancamentoAnual;
import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.financas.CategoriaEC;
import br.com.jetro.entitycontroler.financas.SubCategoriaEC;
import br.com.jetro.modelo.financas.Categoria;
import br.com.jetro.modelo.financas.Lancamento;
import br.com.jetro.modelo.financas.MesRef;
import br.com.jetro.modelo.financas.SubCategoria;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.negocio.financas.LancamentoAS;
import br.com.jetro.negocio.financas.MesReferenciaAS;
import br.com.jetro.negocio.financas.RelatorioAS;
import br.com.jetro.util.GeradorRelatorio;

@Named
@ViewScoped
public class RelatorioBean extends PageCode implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 422252011464581434L;

	@Inject
	private MesReferenciaAS mesReferenciaAS;
	
	@Inject
	private RelatorioAS relatorioAS;
	
	@Inject
	private LancamentoAS lancamentoAS;
	
	@Inject
	private SubCategoriaEC subCategoriaEC;
	
	@Inject
	private CategoriaEC categoriaEC;
	
	private MesRef mesRef;
	
	private Date dataMesRef;

	private Integer ano;
	
	private Categoria categoria;
	
	private SubCategoria subCategoria;
	
	private List<Categoria> categorias;
	
	private List<SubCategoria> subCategorias;
	
	private Integer periodo = 0;
	
	public void prepararRelatorios(){
		
		this.mesRef = new MesRef();
		
		categorias = categoriaEC.listar();
		Collections.sort(categorias);
		
		subCategoria = new SubCategoria();
		subCategorias = new ArrayList<SubCategoria>();
	}
	
	public void atualizarListaSubCategoria(AjaxBehaviorEvent event){
		
		if(categoria != null){
			this.subCategorias = subCategoriaEC.listarPorCategoria(categoria.getId());
			Collections.sort(subCategorias);
		}else{
			subCategoria = new SubCategoria();
			subCategorias = new ArrayList<SubCategoria>();
		}
	}
	
	public void atualizarMes() throws NegocioException{
		mesRef = mesReferenciaAS.consultarMesRefPorData(dataMesRef);
	}
	
	public void relatorioResumoMensal(){
		 try {
			atualizarMes(); 
			RelatorioResumoMensal relResumoMensal = relatorioAS.consultarRelatorioResumoMensal(mesRef);
			
			List<RelatorioResumoMensal> dados = new ArrayList<RelatorioResumoMensal>();
			dados.add(relResumoMensal);
			
			ServletContext servletContext =  (ServletContext) getContext().getExternalContext().getContext();
			String relatorio = servletContext.getRealPath("/relatorios/relatorioMensal.jasper");
			String subRelatorio = servletContext.getRealPath("/relatorios/subRelatorioResumo.jasper");
			
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("SUBREPORT_DIR", subRelatorio);
			param.put("IMG", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			GeradorRelatorio.gerarRelatorio(relatorio,"RelatorioResumidoMensal", param, dados);
			
		
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void relatorioDetalhadoMensal(){
		
		try {
			atualizarMes();

			List<Lancamento> lista = relatorioAS.consultarRelatorioDetalhadoMensal(mesRef);
			
			ServletContext servletContext =  (ServletContext) getContext().getExternalContext().getContext();
			String relatorio = servletContext.getRealPath("/relatorios/relatorioDetalhadoMensal.jasper");
			
			Double totalCredito = lancamentoAS.retornarTotalReceitas(mesRef);
			Double totalDebito = lancamentoAS.retornarTotalDespesas(mesRef);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("IMG", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("TOTAL_CREDITO", totalCredito);
			param.put("TOTAL_DEBITO", totalDebito);
			
			GeradorRelatorio.gerarRelatorio(relatorio,"RelatorioDetalhadoMensal", param, lista);
			
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		} 
	}
	
	public void relatorioAnual(){
		
		try {
			RelatorioGeral relatorioGeral = relatorioAS.consultarRelatorioGeral(ano, periodo);
			
			List<RelatorioGeral> lista = new ArrayList<RelatorioGeral>();
			lista.add(relatorioGeral);
			
			ServletContext servletContext =  (ServletContext) getContext().getExternalContext().getContext();
			String relatorio = servletContext.getRealPath("/relatorios/relatorioGeral.jasper");
			String subRelatorio = servletContext.getRealPath("/relatorios/");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("SUBREPORT_DIR", subRelatorio);
			param.put("IMG", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_1", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_2", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_3", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_4", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_5", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_6", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_7", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_8", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			param.put("IMG_9", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			
			GeradorRelatorio.gerarRelatorio(relatorio,"RelatorioGeral", param, lista);
			
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		} 
	}
	
	public void relatorioAnualPorSubCategoria(){
		
		try {
			if(subCategoria == null){
				throw new NegocioException(ConstantesMensagem.MN023);
			}
			List<ResumoLancamentoAnual> relatorioAnualPorSubCategoria = relatorioAS.consultarRelatorioAnualPorSubCategoria(ano, periodo, subCategoria.getId());
			
			List<RelatorioGeral> lista = new ArrayList<RelatorioGeral>();
			RelatorioGeral relatorioGeral = new RelatorioGeral(ano, periodo);
			relatorioGeral.setResumoLancamentoPorSubCategoria(relatorioAnualPorSubCategoria);
			lista.add(relatorioGeral);
			
			ServletContext servletContext =  (ServletContext) getContext().getExternalContext().getContext();
			String relatorio = servletContext.getRealPath("/relatorios/relatorioAnualPorSubCategoria.jasper");
			String subRelatorio = servletContext.getRealPath("/relatorios/");
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("SUBREPORT_DIR", subRelatorio);
			param.put("IMG", new FileInputStream(servletContext.getRealPath("/resources/imagens/logo.png")));
			
			GeradorRelatorio.gerarRelatorio(relatorio,"Relatorio Anual de "+relatorioAnualPorSubCategoria.get(0).getDescricao(), param, lista);
			
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		} 
	}
	
	public Date getDataMesRef() {
		return dataMesRef;
	}

	public void setDataMesRef(Date dataMesRef) {
		this.dataMesRef = dataMesRef;
	}

	public MesRef getMesRef() {
		return mesRef;
	}

	public void setMesRef(MesRef mesRef) {
		this.mesRef = mesRef;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
