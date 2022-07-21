package br.com.jetro.negocio.financas;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.entitycontroler.financas.MesRefEC;
import br.com.jetro.modelo.financas.MesRef;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.util.Transactional;

public class MesReferenciaAS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3690517784872314314L;
	
	@Inject
	private MesRefEC mesRefEC;
	
	@Inject
	private LancamentoAS lancamentoAS; 
	
	@Transactional
	public void criarMes(MesRef mes) throws NegocioException{
		
		MesRef mesAtual = mesRefEC.buscarMesAtual();
		
		if(mesAtual != null){
			validarMesRef(mes, mesAtual);
			calcularSaldo(mesAtual);
			mesAtual.setDataDesativacao(new Date());
			mesRefEC.guardar(mesAtual);
		}
		
		mes.setDataAtivacao(new Date());
		mes.setValorSaldo(0.0);
		mesRefEC.guardar(mes);
	}
	

	public void calcularSaldo(MesRef mesAtual) {
		
		Double saldo = 0.0;
		
		if(mesAtual.getDataDesativacao() != null){
			saldo += mesAtual.getValorSaldo();
		}else{
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(mesAtual.getData());
		
			MesRef mesAnterior = null;
		
			do{
				calendar.add(Calendar.MONTH, -1);
				mesAnterior = mesRefEC.consultarMesRefPorData(calendar.getTime());
				
			}while(mesAnterior == null);
		
			saldo = mesAnterior.getValorSaldo();
			saldo += lancamentoAS.retornarSaldoMes(mesAtual);
		}
		mesAtual.setValorSaldo(saldo);
	}


	public MesRef buscarMesAtual() {
		return mesRefEC.buscarMesAtual();
	}




	private void validarMesRef(MesRef mes, MesRef mesAtual) throws NegocioException {
		
		Calendar mesRef = Calendar.getInstance();
		mesRef.setTime(mes.getData());
		
		Calendar mesRefAtual = Calendar.getInstance();
		mesRefAtual.setTime(mesAtual.getData());
		
		if(mesRef.get(Calendar.YEAR) == mesRefAtual.get(Calendar.YEAR)
				&& mesRef.get(Calendar.MONTH) == mesRefAtual.get(Calendar.MONTH)){
			throw new NegocioException(ConstantesMensagem.MN009);
		}
		
		if(mesRef.get(Calendar.YEAR) <= mesRefAtual.get(Calendar.YEAR) &&
				mesRef.get(Calendar.MONTH) < mesRefAtual.get(Calendar.MONTH)){
			throw new NegocioException(ConstantesMensagem.MN010);
		}
		
		Calendar dataHoje = Calendar.getInstance();
		dataHoje.set(Calendar.HOUR, 0);
		dataHoje.set(Calendar.MINUTE, 0);
		dataHoje.set(Calendar.SECOND, 0);
		dataHoje.set(Calendar.MILLISECOND, 0);
		
		mesRefAtual.set(Calendar.DAY_OF_MONTH, mesRefAtual.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		if(dataHoje.getTime().before(mes.getData()) || !dataHoje.getTime().after(mesRefAtual.getTime())){
			throw new NegocioException(ConstantesMensagem.MN011);
		}
	}

	public MesRef consultarMesRefPorData(Date data) throws NegocioException {
		if(data == null){
			throw new NegocioException(ConstantesMensagem.MN014);
		}
		MesRef mes =  mesRefEC.consultarMesRefPorData(data);
		if(mes == null){
			throw new NegocioException(ConstantesMensagem.MN012);
		}
		return mes;
	}
	
	public List<MesRef> listarTodos(){
		return this.mesRefEC.listar();
	}
}
