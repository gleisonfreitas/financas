package br.com.jetro.beans.membresia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.MembroEC;
import br.com.jetro.modelo.membresia.Membro;
import br.com.jetro.util.GeradorRelatorio;

@Named
@ViewScoped
public class RelatorioMembroBean extends PageCode implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 422252011464581434L;
	
	@Inject
	private MembroEC membroEC;
	
	private Membro membro;
	
	private List<SelectItem> mesAnivSelec;
	
	private Integer mesAniv;
	
	@PostConstruct
	public void inicializar(){
		
		mesAnivSelec = new ArrayList<>();
		mesAnivSelec.add(new SelectItem(1, "Janeiro"));
		mesAnivSelec.add(new SelectItem(2, "Feveiro"));
		mesAnivSelec.add(new SelectItem(3, "Março"));
		mesAnivSelec.add(new SelectItem(4, "Abril"));
		mesAnivSelec.add(new SelectItem(5, "Maio"));
		mesAnivSelec.add(new SelectItem(6, "Junho"));
		mesAnivSelec.add(new SelectItem(7, "Julho"));
		mesAnivSelec.add(new SelectItem(8, "Agosto"));
		mesAnivSelec.add(new SelectItem(9, "Setembro"));
		mesAnivSelec.add(new SelectItem(10, "Outubro"));
		mesAnivSelec.add(new SelectItem(11, "Novembro"));
		mesAnivSelec.add(new SelectItem(12, "Dezembro"));
	}
	
	public void visualizar(){
		 try {
			 
			List<Membro> dados = new ArrayList<Membro>();
			dados.add(membro);
			
			
			String relatorio = getServletContext().getRealPath("/relatorios/relatorioMembro.jasper");
			
			Map<String, Object> param = new HashMap<String, Object>();
			FileInputStream logo = new FileInputStream(getServletContext().getRealPath("/resources/imagens/logo.png"));
			param.put("IMG", logo);
			String foto = membro.getIdentificacao().getFoto();
			FileInputStream fileInputStream = null;
			if(foto != null){
				try {
					fileInputStream = new FileInputStream("C:\\JetroFotos\\"+membro.getIdentificacao().getFoto());
				} catch (FileNotFoundException e) {
					fileInputStream = new FileInputStream(getServletContext().getRealPath("/resources/imagens/perfil.jpg"));
				}
			}else{
				fileInputStream = new FileInputStream(getServletContext().getRealPath("/resources/imagens/perfil.jpg"));
			}
			param.put("FOTO", fileInputStream);
			GeradorRelatorio.gerarRelatorio(relatorio,"RelatorioMembro", param, dados);
			
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void relatorioAnivMensal(){
		
		try {
			
			if(mesAniv == null){
				adicionarMensagem(ConstantesMensagem.MN026, FacesMessage.SEVERITY_WARN);
			}else{
				
				List<Membro> dados = membroEC.listarAniversariantesPorMes(mesAniv);
				
				if(dados.isEmpty()){
					adicionarMensagem(ConstantesMensagem.MN027, FacesMessage.SEVERITY_WARN);
				}else{
					
					Collections.sort(dados);

					String relatorio = getServletContext().getRealPath("/relatorios/relatorioMembroAniver.jasper");
					Map<String, Object> param = new HashMap<String, Object>();
					FileInputStream logo = new FileInputStream(getServletContext().getRealPath("/resources/imagens/logo.png"));
					param.put("IMG", logo);
					GeradorRelatorio.gerarRelatorio(relatorio,"Relatorio Aniversariantes do Mês", param, dados);
				}
			}
			
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		}
		
	}
	
	public void relatorioGeral(){
		
		try {
			
			List<Membro> dados = membroEC.listar();
			
			if(dados.isEmpty()){
				adicionarMensagem(ConstantesMensagem.MN028, FacesMessage.SEVERITY_WARN);
			}else{
	
				String relatorio = getServletContext().getRealPath("/relatorios/relatorioMembroGeral.jasper");
				Map<String, Object> param = new HashMap<String, Object>();
				FileInputStream logo = new FileInputStream(getServletContext().getRealPath("/resources/imagens/logo.png"));
				param.put("IMG", logo);
				GeradorRelatorio.gerarRelatorio(relatorio,"Relatorio Geral", param, dados);
			}
			
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN019, FacesMessage.SEVERITY_ERROR);
		}
		
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public List<SelectItem> getMesAnivSelec() {
		return mesAnivSelec;
	}

	public void setMesAnivSelec(List<SelectItem> mesAnivSelec) {
		this.mesAnivSelec = mesAnivSelec;
	}

	public Integer getMesAniv() {
		return mesAniv;
	}

	public void setMesAniv(Integer mesAniv) {
		this.mesAniv = mesAniv;
	}
}
