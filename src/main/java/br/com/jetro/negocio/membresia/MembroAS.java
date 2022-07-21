package br.com.jetro.negocio.membresia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.membresia.HistoricoFuncaoEC;
import br.com.jetro.entitycontroler.membresia.HistoricoLiderancaEC;
import br.com.jetro.entitycontroler.membresia.MembroEC;
import br.com.jetro.modelo.membresia.HistoricoFuncao;
import br.com.jetro.modelo.membresia.HistoricoLideranca;
import br.com.jetro.modelo.membresia.Membro;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.util.Transactional;

public class MembroAS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private MembroEC membroEC;
	
	@Inject
	private HistoricoLiderancaEC historicoLiderancaEC;
	
	@Inject
	private HistoricoFuncaoEC historicoFuncaoEC;
	
	@Transactional
	public void salvar(Membro membro, UploadedFile uploadFile) throws NegocioException {
		membro.setDataCadastro(new Date());
		membro.getDadosComplementares().setCongregando("S");
		criarNomeFoto(membro);
		verificarHistoricoLideranca(membro);
		verificarHistoricoFuncao(membro);
		membroEC.guardar(membro);
		salvarFoto(membro, uploadFile);
	}

	private void verificarHistoricoFuncao(Membro membro) {
		if(membro.getId() != null){
			List<Long> ids = retornarIdHistoricoFuncao(membro.getDadosEclesiastico().getFuncoes());
			if(!ids.isEmpty()){
				historicoFuncaoEC.excluirHistoricos(membro.getDadosEclesiastico().getId(), ids);
			}else{
				historicoFuncaoEC.excluirTodosHistoricos(membro.getDadosEclesiastico().getId());
			}
		}
		
	}

	private List<Long> retornarIdHistoricoFuncao(List<HistoricoFuncao> funcoes) {
		
		List<Long> ids = new ArrayList<Long>();
		
		for (HistoricoFuncao historicoFuncao : funcoes) {
			if(historicoFuncao.getId() != null){
				ids.add(historicoFuncao.getId());
			}
		}
		
		return ids;
	}

	private void verificarHistoricoLideranca(Membro membro) {
		if(membro.getId() != null){
			List<Long> ids = retornarIdHistoricoLideranca(membro.getDadosEclesiastico().getLiderancas());
			if(!ids.isEmpty()){
				historicoLiderancaEC.excluirHistoricos(membro.getDadosEclesiastico().getId(), ids);
			}else{
				historicoLiderancaEC.excluirTodosHistoricos(membro.getDadosEclesiastico().getId());
			}
		}
	}
	
	private List<Long> retornarIdHistoricoLideranca(List<HistoricoLideranca> liderancas) {
		
		List<Long> ids = new ArrayList<Long>();
		
		for (HistoricoLideranca historicoLideranca : liderancas) {
			if(historicoLideranca.getId() != null){
				ids.add(historicoLideranca.getId());
			}
		}
		
		return ids;
	}

	private void salvarFoto(Membro membro, UploadedFile uploadFile) throws NegocioException {
		if(uploadFile != null){
			try {
				File file = new File("C:\\JetroFotos", membro.getIdentificacao().getFoto());
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(uploadFile.getContents());
				fos.close();
			} catch(Exception e){
				throw new NegocioException(ConstantesMensagem.MN024, e);
	        }
		}
	}
	
	private void criarNomeFoto(Membro membro){
		long time = new Date().getTime();
		String hex = Long.toHexString(time);
		
		membro.getIdentificacao().setFoto(
				hex+
				"_"+
				retornarNomeContatenado(membro.getIdentificacao().getNome())+
				".jpg");
	}
	
	private String retornarNomeContatenado(String nome) {
		return nome.trim().replaceAll(" ", "_");
	}

	@Transactional
	public void remover(Long idExclusao) {
		Membro membro = membroEC.bucarPorId(idExclusao);
		membroEC.remover(membro);
	}
	
}
