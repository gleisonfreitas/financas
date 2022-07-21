package br.com.jetro.beans.membresia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.CidadeEC;
import br.com.jetro.entitycontroler.membresia.EstadoEC;
import br.com.jetro.entitycontroler.membresia.FuncaoEC;
import br.com.jetro.entitycontroler.membresia.LiderancaEC;
import br.com.jetro.entitycontroler.membresia.ProfissaoEC;
import br.com.jetro.enums.EstadoCivilEnum;
import br.com.jetro.enums.NacionalidadeEnum;
import br.com.jetro.enums.SexoEnum;
import br.com.jetro.modelo.membresia.Cidade;
import br.com.jetro.modelo.membresia.Estado;
import br.com.jetro.modelo.membresia.Funcao;
import br.com.jetro.modelo.membresia.HistoricoFuncao;
import br.com.jetro.modelo.membresia.HistoricoLideranca;
import br.com.jetro.modelo.membresia.Lideranca;
import br.com.jetro.modelo.membresia.Membro;
import br.com.jetro.modelo.membresia.Profissao;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.negocio.membresia.CidadeAS;
import br.com.jetro.negocio.membresia.MembroAS;
import br.com.jetro.negocio.membresia.ProfissaoAS;

@Named
@ViewScoped
public class MembroBean extends PageCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoEC estadoEC;
	
	@Inject
	private CidadeEC cidadeEC;
	
	@Inject
	private ProfissaoEC profissaoEC;
	
	@Inject
	private FuncaoEC funcaoEC;
	
	@Inject
	private LiderancaEC liderancaEC;
	
	@Inject
	private MembroAS membroAS;
	
	@Inject
	private CidadeAS cidadeAS;
	
	@Inject
	private ProfissaoAS profissaoAS;
	
	private Membro membro;
	
	private Estado estado;
	
	private Estado estadoNat;
	
	private Cidade cidade;
	
	private Profissao profissao;
	
	private List<Estado> listaEstados;
	
	private List<Cidade> listaCidades;
	
	private List<Estado> listaEstadosNat;
	
	private List<Cidade> listaCidadesNat;
	
	private List<Profissao> listaProfissoes;
	
	private List<Funcao> listaFuncoes;
	
	private List<Lideranca> listaLiderancas;
	
	private HistoricoFuncao historicoFuncao;
	
	private HistoricoLideranca historicoLideranca;
	
	private String foto;

	private UploadedFile uploadFile;
	
	private boolean addCidadeEnd;
	
	private boolean addCidadeDC;
	
	private boolean mostraBtAltLideranca;

	private boolean mostraBtAltFuncao;
	
	public void preparar(){
		
		if(membro != null && membro.getId() != null){
			this.estado = membro.getEndereco().getCidade().getEstado();
			this.estadoNat = membro.getDadosComplementares().getNaturalidade().getEstado();
			listaCidadesNat = cidadeEC.listarPorEstado(this.estadoNat.getId());
			listaCidades = cidadeEC.listarPorEstado(this.estado.getId());
			foto = "/imagens/"+this.membro.getIdentificacao().getFoto();
		}else{
			this.membro = new Membro();
			this.foto = null;
		}
		this.uploadFile = null;
		this.historicoFuncao = new HistoricoFuncao(this.membro.getDadosEclesiastico());
		this.historicoLideranca = new HistoricoLideranca(this.membro.getDadosEclesiastico());
		this.profissao = new Profissao();
		this.cidade = new Cidade();
		prepararListas();
	}
	
	public void prepararListas(){
		this.listaEstados = estadoEC.listar();
		this.listaEstadosNat = estadoEC.listar();
		this.listaLiderancas = liderancaEC.listar();
		this.listaProfissoes = profissaoEC.listar();
		this.listaFuncoes = funcaoEC.listar();
		
	}
	
	public void atualizarListaCidades(AjaxBehaviorEvent event){
		if(this.estado != null && 
				this.estado.getId() != null){
			listaCidades = cidadeEC.listarPorEstado(estado.getId());
			Collections.sort(listaCidades);
		}else{
			listaCidades.clear();
			this.membro.getEndereco().setCidade(new Cidade());
		}
	}
	
	public void addFuncao(){
		boolean adiciona = true;
		if(this.historicoFuncao.getFuncao() == null){
			adicionarMensagemObrigatorio("Função");
            adiciona = false;
		}
		if(this.historicoFuncao.getDataOrdenacao() == null){
			adicionarMensagemObrigatorio("Data Ordenação");
            adiciona = false;
		}
		if(adiciona){
			this.membro.getDadosEclesiastico().getFuncoes().add(this.historicoFuncao);
			this.historicoFuncao = new HistoricoFuncao(this.membro.getDadosEclesiastico());
		}
	}
	
	public void altFuncao(){
		this.historicoFuncao = new HistoricoFuncao();
		this.mostraBtAltFuncao = false;
	}
	
	public void setRemoverFuncao(HistoricoFuncao historicoFuncao){
		List<HistoricoFuncao> funcoes = this.membro.getDadosEclesiastico().getFuncoes();
		funcoes.remove(historicoFuncao);
		this.membro.getDadosEclesiastico().setFuncoes(new ArrayList<HistoricoFuncao>());
		this.membro.getDadosEclesiastico().getFuncoes().addAll(funcoes);
	}
	
	public void addLideranca(){
		boolean adiciona = true;
		if(this.historicoLideranca.getLideranca() == null){
			adicionarMensagemObrigatorio("liderança");
            adiciona = false;
		}
		if(this.historicoLideranca.getDataInicio() == null){
			adicionarMensagemObrigatorio("Data Início");
            adiciona = false;
		}
		if(adiciona){
			this.membro.getDadosEclesiastico().getLiderancas().add(this.historicoLideranca);
			this.historicoLideranca = new HistoricoLideranca(this.membro.getDadosEclesiastico());
		}
	}
	
	public void altLideranca(){
		this.historicoLideranca = new HistoricoLideranca();
		this.mostraBtAltLideranca = false;
	}
	
	public void setRemoverLideranca(HistoricoLideranca historicoLideranca){
		List<HistoricoLideranca> liderancas = this.membro.getDadosEclesiastico().getLiderancas();
		liderancas.remove(historicoLideranca);
		this.membro.getDadosEclesiastico().setLiderancas(new ArrayList<HistoricoLideranca>());
		this.membro.getDadosEclesiastico().getLiderancas().addAll(liderancas);
	}
	
	public void atualizarListaCidadesNat(AjaxBehaviorEvent event){
		if(this.estadoNat != null && 
				this.estadoNat.getId() != null){
			listaCidadesNat = cidadeEC.listarPorEstado(estadoNat.getId());
			Collections.sort(listaCidadesNat);
		}else{
			listaCidadesNat.clear();
			this.membro.getDadosComplementares().setNaturalidade(new Cidade());
			
		}
	}
	
	public void salvar(){
		try {
			membroAS.salvar(this.membro, uploadFile);
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
			this.membro = new Membro();
			preparar();
			this.estado = new Estado();
			listaCidades.clear();
			this.estadoNat = new Estado();
			listaCidadesNat.clear();
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_INFO);
		}
	}
	
	 public void dlgEdLideranca() {
	        Map<String,Object> options = new HashMap<String, Object>();
	        options.put("modal", true);
	        options.put("width", 300);
	        options.put("height", 250);
	        options.put("contentWidth", "100%");
	        options.put("contentHeight", "100%");
	        options.put("headerElement", "customheader");
	         
	        RequestContext.getCurrentInstance().openDialog("dlgEdLideranca", options, null);
	 }
	
	public void salvarCidade(){
		try {
			if(addCidadeDC){
				addCidadeDC = false;
				this.cidade.setEstado(this.estadoNat);
				this.cidade = cidadeAS.salvar(this.cidade);
				listaCidadesNat = cidadeEC.listarPorEstado(this.estadoNat.getId());
				this.membro.getDadosComplementares().setNaturalidade(this.cidade);
			}else if(addCidadeEnd){
				addCidadeEnd = false;
				this.cidade.setEstado(this.estado);
				this.cidade = cidadeAS.salvar(this.cidade);
				listaCidades = cidadeEC.listarPorEstado(this.estado.getId());
				this.membro.getEndereco().setCidade(this.cidade);
			}
			this.cidade = new Cidade();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void salvarProfissao(){
		try {
			this.profissao = profissaoAS.salvar(this.profissao);
			this.listaProfissoes = profissaoEC.listar();
			this.membro.getDadosComplementares().setProfissao(this.profissao);;
			this.profissao = new Profissao();
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		
		uploadFile = event.getFile();
		
        if (uploadFile != null) {
		
        	File fileTemp = new File("C:\\JetroFotos\\temp", uploadFile.getFileName()); 
	        try {
	            FileOutputStream fos = new FileOutputStream(fileTemp);
	            fos.write(uploadFile.getContents());
	            fos.close();
	            foto = "/imagenstemp/"+uploadFile.getFileName();
	            
	            FacesMessage message = new FacesMessage("Sucesso", event.getFile().getFileName() + " foi carregado.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }catch(Exception e){
	        	adicionarMensagem(ConstantesMensagem.MN024, FacesMessage.SEVERITY_INFO);
	        }
        }
    }
	
	public SexoEnum[] getListaSexo(){
		
		return SexoEnum.values();
	}
	
	public EstadoCivilEnum[] getListaEstadoCivil(){
		
		return EstadoCivilEnum.values();
	}
	
	public NacionalidadeEnum[] getListaNacionalidade(){
		
		return NacionalidadeEnum.values();
	}


	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public List<Profissao> getListaProfissoes() {
		return listaProfissoes;
	}

	public void setListaProfissoes(List<Profissao> listaProfissoes) {
		this.listaProfissoes = listaProfissoes;
	}
	
	public Estado getEstadoNat() {
		return estadoNat;
	}

	public void setEstadoNat(Estado estadoNat) {
		this.estadoNat = estadoNat;
	}

	public List<Estado> getListaEstadosNat() {
		return listaEstadosNat;
	}

	public void setListaEstadosNat(List<Estado> listaEstadosNat) {
		this.listaEstadosNat = listaEstadosNat;
	}

	public List<Cidade> getListaCidadesNat() {
		return listaCidadesNat;
	}

	public void setListaCidadesNat(List<Cidade> listaCidadesNat) {
		this.listaCidadesNat = listaCidadesNat;
	}

	public HistoricoFuncao getHistoricoFuncao() {
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao) {
		this.historicoFuncao = historicoFuncao;
	}

	public List<Funcao> getListaFuncoes() {
		return listaFuncoes;
	}

	public void setListaFuncoes(List<Funcao> listaFuncoes) {
		this.listaFuncoes = listaFuncoes;
	}

	public List<Lideranca> getListaLiderancas() {
		return listaLiderancas;
	}

	public void setListaLiderancas(List<Lideranca> listaLiderancas) {
		this.listaLiderancas = listaLiderancas;
	}

	public HistoricoLideranca getHistoricoLideranca() {
		return historicoLideranca;
	}

	public void setHistoricoLideranca(HistoricoLideranca historicoLideranca) {
		this.historicoLideranca = historicoLideranca;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public boolean isAddCidadeEnd() {
		return addCidadeEnd;
	}

	public void setAddCidadeEnd(boolean addCidadeEnd) {
		this.addCidadeEnd = addCidadeEnd;
	}

	public boolean isAddCidadeDC() {
		return addCidadeDC;
	}

	public void setAddCidadeDC(boolean addCidadeDC) {
		this.addCidadeDC = addCidadeDC;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public boolean isMostraBtAltLideranca() {
		return mostraBtAltLideranca;
	}

	public void setMostraBtAltLideranca(boolean mostraBtAltLideranca) {
		this.mostraBtAltLideranca = mostraBtAltLideranca;
	}

	public boolean isMostraBtAltFuncao() {
		return mostraBtAltFuncao;
	}

	public void setMostraBtAltFuncao(boolean mostraBtAltFuncao) {
		this.mostraBtAltFuncao = mostraBtAltFuncao;
	}
	
}
