package br.com.jetro.entitycontroler.membresia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import br.com.jetro.assistentes.MembroFiltro;
import br.com.jetro.entitycontroler.EntityControlerGenerics;
import br.com.jetro.modelo.membresia.HistoricoFuncao;
import br.com.jetro.modelo.membresia.HistoricoLideranca;
import br.com.jetro.modelo.membresia.Membro;
import br.com.jetro.util.Util;

public class MembroEC extends EntityControlerGenerics<Membro> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Membro> consultarPorFiltro(MembroFiltro filtro) {
		
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("SELECT m from Membro m ");
		boolean primeiroWhere = true;
		if(filtro.getNome() != null && !filtro.getNome().trim().isEmpty()){
			if(primeiroWhere){
				sb.append("WHERE upper(m.identificacao.nome) like upper(:nome) ");
				primeiroWhere = false;
			}
			params.put("nome", "%"+filtro.getNome()+"%");
		}
		if(filtro.getCpf() != null && !filtro.getCpf().trim().isEmpty()){
			if(primeiroWhere){
				sb.append("WHERE m.dadosComplementares.cpf = :cpf ");
				primeiroWhere = false;
			}else{
				sb.append("AND m.dadosComplementares.cpf = :cpf ");
			}
			params.put("cpf", Util.removerFormatacao(filtro.getCpf()));
		}
		if(filtro.getDataInicio() != null && filtro.getDataFim() != null){
			if(primeiroWhere){
				sb.append("WHERE m.dadosComplementares.dataNascimento between :dataInicio AND :dataFim ");
				primeiroWhere = false;
			}else{
				sb.append("AND m.dadosComplementares.dataNascimento between :dataInicio AND :dataFim ");
			}
			params.put("dataInicio", filtro.getDataInicio());
			params.put("dataFim", filtro.getDataFim());
		}else if(filtro.getDataInicio() != null){
			if(primeiroWhere){
				sb.append("WHERE m.dadosComplementares.dataNascimento >= :dataInicio ");
				primeiroWhere = false;
			}else{
				sb.append("AND m.dadosComplementares.dataNascimento >= :dataInicio ");
			}
			params.put("dataInicio", filtro.getDataInicio());
		}else if(filtro.getDataFim() != null){
			if(primeiroWhere){
				sb.append("WHERE m.dadosComplementares.dataNascimento <= :dataFim ");
				primeiroWhere = false;
			}else{
				sb.append("AND m.dadosComplementares.dataNascimento <= :dataFim ");
			}
			params.put("dataFim", filtro.getDataFim());
		}
		
		Query query = entityManager.createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<Membro> resultList = query.getResultList();
		
		return processarListas(resultList);
	}

	private List<Membro> processarListas(List<Membro> resultList) {
		
		for (Membro membro : resultList) {
			
			List<HistoricoFuncao> funcoes = new ArrayList<HistoricoFuncao>();
			for (HistoricoFuncao historicoFuncao : membro.getDadosEclesiastico().getFuncoes()) {
				funcoes.add(historicoFuncao);
			}
			Collections.reverse(funcoes);
			membro.getDadosEclesiastico().setFuncoes(funcoes);
			List<HistoricoLideranca> liderancas = new ArrayList<HistoricoLideranca>();
			for (HistoricoLideranca historicoLideranca : membro.getDadosEclesiastico().getLiderancas()) {
				liderancas.add(historicoLideranca);
			}
			Collections.reverse(liderancas);
			membro.getDadosEclesiastico().setLiderancas(liderancas);
		}
		
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Membro> listarAniversariantesPorMes(Integer mesAniv) {
		
		return entityManager.createNamedQuery("listarAniversariantesPorMes")
				.setParameter("mes", mesAniv)
				.getResultList();
	}

}
