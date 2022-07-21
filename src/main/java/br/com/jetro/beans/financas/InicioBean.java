package br.com.jetro.beans.financas;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.jetro.assistentes.ResumoCategoria;
import br.com.jetro.assistentes.ResumoLancamento;
import br.com.jetro.assistentes.ResumoLancamentoMes;
import br.com.jetro.beans.PageCode;
import br.com.jetro.constantes.ConstantesMensagem;
import br.com.jetro.modelo.financas.MesRef;
import br.com.jetro.negocio.NegocioException;
import br.com.jetro.negocio.financas.LancamentoAS;
import br.com.jetro.negocio.financas.MesReferenciaAS;
import br.com.jetro.util.Util;

@Named
@ViewScoped
public class InicioBean extends PageCode{
	
	
	private static final long serialVersionUID = 3205135856657808779L;
	
	@Inject
	private MesReferenciaAS mesReferenciaAS;
	
	@Inject
	private LancamentoAS lancamentoAS;
	
	private MesRef mesRef;
	
	private Double saldoMes;
	
	private Double totalReceitas;
	
	private Double totalDespesas;
	
	private List<ResumoCategoria> listaReceitas;
	
	private List<ResumoCategoria> listaDespesas;
	
	private BarChartModel barModel;

	private LineChartModel lineModel;

	private HorizontalBarChartModel barDespesas;

	private CartesianChartModel historicoAnual;

	public void prepararMovimentacaoMesAtual(){
		
		MesRef mesRefAtual = mesReferenciaAS.buscarMesAtual();
		mesRef = new MesRef();
		totalReceitas = 0.0;
		totalDespesas = 0.0;
		
		prepararMovimentacao(mesRefAtual);
	}

	private void prepararMovimentacao(MesRef mesRefAtual) {
		saldoMes = 0.0;
		if(mesRefAtual != null){
			mesRef.setData(mesRefAtual.getData());
			mesReferenciaAS.calcularSaldo(mesRefAtual);
			saldoMes = mesRefAtual.getValorSaldo().doubleValue();
			listaReceitas = lancamentoAS.retornarReceitas(mesRefAtual);
			listaDespesas = lancamentoAS.retornarDespesas(mesRefAtual);
			totalReceitas = lancamentoAS.retornarTotalReceitas(mesRefAtual);
			totalDespesas = lancamentoAS.retornarTotalDespesas(mesRefAtual);
			createBarModel();
			createLineModels(mesRefAtual);			
			createBarDespesas(mesRefAtual);
			createResumoAno(mesRefAtual);
			
		}
	}
	
	public void atualizarMovimentacao(){
		try {
			MesRef mes = mesReferenciaAS.consultarMesRefPorData(mesRef.getData());
			prepararMovimentacao(mes);
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void criarMes(){
		try {
			
			mesReferenciaAS.criarMes(mesRef);
			prepararMovimentacaoMesAtual();
			adicionarMensagem(ConstantesMensagem.MN001, FacesMessage.SEVERITY_INFO);
		} catch (NegocioException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception e) {
			adicionarMensagem(ConstantesMensagem.MN002, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Receitas / Despesas");
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("R$");
        yAxis.setMin(0.0);
        
    }

	private BarChartModel initBarModel() {
		
		BarChartModel model = new BarChartModel();

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
		
        ChartSeries receita = new ChartSeries();
        receita.setLabel("Receita");
        receita.set(sdf.format(mesRef.getData()), totalReceitas.doubleValue());

        ChartSeries despesa = new ChartSeries();
        despesa.setLabel("Despesa");
        despesa.set(sdf.format(mesRef.getData()), totalDespesas.doubleValue());
 
        model.addSeries(receita);
        model.addSeries(despesa);
         
        return model;
	}
	
	private void createLineModels(MesRef mesRef) {
         
        lineModel = initCategoryModel(mesRef);
        lineModel.setTitle("Evolução de Receitas");
        lineModel.setLegendPosition("e");
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Dia"));
        lineModel.setAnimate(true);
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("R$");
        yAxis.setTickInterval("5000");
        yAxis.setMin(0);
    }
	
	private LineChartModel initCategoryModel(MesRef mesRef) {
        
		List<ResumoLancamento> listaReceitasAoDia = lancamentoAS.listaReceitasAoDia(mesRef);
		
		
		LineChartModel model = new LineChartModel();
 
        ChartSeries receitas = new ChartSeries();
        receitas.setLabel("Receitas");
        for (ResumoLancamento resumoLancamento : listaReceitasAoDia) {
        	receitas.set(resumoLancamento.getDescricao(), resumoLancamento.getValor());
		}

        model.addSeries(receitas);
         
        return model;
    }
	
	private void createBarDespesas(MesRef mes) {
        
		barDespesas = new HorizontalBarChartModel();
		
		List<ResumoLancamento> lista = lancamentoAS.retornarValorPorDespesas(mes);
		Collections.sort(lista);
		
		ChartSeries despesa = new ChartSeries("Despesas");
        for (ResumoLancamento resumo : lista) {
        	despesa.set(resumo.getDescricao(), resumo.getValor());
		}

        barDespesas.addSeries(despesa);
         
        Axis subcategoria = new CategoryAxis();
		barDespesas.getAxes().put(AxisType.Y, subcategoria );
        barDespesas.setAnimate(true);
        barDespesas.setLegendPosition("ne");
         
        Axis xAxis = barDespesas.getAxis(AxisType.X);
        xAxis.setLabel("R$");
        xAxis.setMin(0.0);
        xAxis.setTickInterval("1000");
        
    }
	
	private void createResumoAno(MesRef mes) {
		
        historicoAnual = new BarChartModel();
        
        List<ResumoLancamentoMes> lista = lancamentoAS.retornarResumoMesUltimosDozesMeses(mes);
        Collections.sort(lista);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        
        BarChartSeries creditos = new BarChartSeries();
        BarChartSeries debitos = new BarChartSeries();
        LineChartSeries saldo = new LineChartSeries();
        LineChartSeries saldoAcumulado = new LineChartSeries();

        creditos.setLabel("Receitas");
        debitos.setLabel("Depesas");
        saldo.setLabel("Saldo");
        saldoAcumulado.setLabel("Saldo Acumulado");
        
        Double acumulado = 0.0;
        
        for (ResumoLancamentoMes resumo : lista) {
        	creditos.set(sdf.format(resumo.getData()), resumo.getCredito());
        	debitos.set(sdf.format(resumo.getData()), resumo.getDebito());
        	saldo.set(sdf.format(resumo.getData()), resumo.getSaldo());
        	
        	acumulado += resumo.getSaldo();
        	saldoAcumulado.set(sdf.format(resumo.getData()), acumulado.doubleValue());
		}
 
        historicoAnual.addSeries(creditos);
        historicoAnual.addSeries(debitos);
        historicoAnual.addSeries(saldo);
        historicoAnual.addSeries(saldoAcumulado);
         
        historicoAnual.setTitle("Histórico de "+new SimpleDateFormat("yyyy").format(mes.getData()));
        historicoAnual.setLegendPosition("n");
        historicoAnual.setLegendRows(1);
        historicoAnual.setMouseoverHighlight(false);
        historicoAnual.setShowDatatip(false);
        historicoAnual.setShowPointLabels(true);
        historicoAnual.setAnimate(true);
        
        Axis meses = new CategoryAxis();
        meses.setTickAngle(90);
		historicoAnual.getAxes().put(AxisType.X, meses );
        
    }
	
	public boolean isTipoSaldo(){
		return this.saldoMes > 0;
	}

	public MesRef getMesRef() {
		return mesRef;
	}

	public void setMesRef(MesRef mesRef) {
		this.mesRef = mesRef;
	}

	public Double getSaldoMes() {
		return saldoMes;
	}

	public void setSaldoMes(Double saldoMes) {
		this.saldoMes = saldoMes;
	}

	public List<ResumoCategoria> getListaReceitas() {
		return listaReceitas;
	}

	public void setListaReceitas(List<ResumoCategoria> listaReceitas) {
		this.listaReceitas = listaReceitas;
	}

	public List<ResumoCategoria> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(List<ResumoCategoria> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public String getTotalReceitas() {
		return Util.retornarNumeroBr(totalReceitas.doubleValue());
	}
	

	public void setTotalReceitas(Double totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public String getTotalDespesas() {
		return Util.retornarNumeroBr(totalDespesas.doubleValue());
	}

	public void setTotalDespesas(Double totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public HorizontalBarChartModel getBarDespesas() {
		return barDespesas;
	}

	public void setBarDespesas(HorizontalBarChartModel barDespesas) {
		this.barDespesas = barDespesas;
	}

	public CartesianChartModel getHistoricoAnual() {
		return historicoAnual;
	}

	public void setHistoricoAnual(CartesianChartModel historicoAnual) {
		this.historicoAnual = historicoAnual;
	}
}
