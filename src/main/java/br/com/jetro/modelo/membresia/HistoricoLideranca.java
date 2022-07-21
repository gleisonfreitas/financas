package br.com.jetro.modelo.membresia;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="historico_lideranca")
@NamedQueries(
		{@NamedQuery(name="excluirHistorico",query="DELETE FROM HistoricoLideranca h WHERE h.dadosEclesiastico.id = :idDadosEclesiastico AND h.id NOT IN (:ids))"),
		 @NamedQuery(name="excluirTodosHistorico",query="DELETE FROM HistoricoLideranca h WHERE h.dadosEclesiastico.id = :idDadosEclesiastico")})
public class HistoricoLideranca implements Serializable, Comparable<HistoricoLideranca>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_historico_lideranca", sequenceName = "seq_historico_lideranca", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_historico_lideranca")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_dados_eclesiastico")
	private DadosEclesiastico dadosEclesiastico;
	
	@ManyToOne
	@JoinColumn(name = "id_lideranca")
	private Lideranca lideranca;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_fim")
	private Date dataFim;
	
	public HistoricoLideranca() {
		super();
	}
	
	public HistoricoLideranca(DadosEclesiastico dadosEclesiastico) {
		this.dadosEclesiastico = dadosEclesiastico;
	}
	
	@Override
	public int compareTo(HistoricoLideranca o) {
		return this.dataInicio.compareTo(o.getDataInicio());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DadosEclesiastico getDadosEclesiastico() {
		return dadosEclesiastico;
	}

	public void setDadosEclesiastico(DadosEclesiastico dadosEclesiastico) {
		this.dadosEclesiastico = dadosEclesiastico;
	}

	public Lideranca getLideranca() {
		return lideranca;
	}

	public void setLideranca(Lideranca lideranca) {
		this.lideranca = lideranca;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dadosEclesiastico == null) ? 0 : dadosEclesiastico.hashCode());
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lideranca == null) ? 0 : lideranca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoLideranca other = (HistoricoLideranca) obj;
		if (dadosEclesiastico == null) {
			if (other.dadosEclesiastico != null)
				return false;
		} else if (!dadosEclesiastico.equals(other.dadosEclesiastico))
			return false;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lideranca == null) {
			if (other.lideranca != null)
				return false;
		} else if (!lideranca.equals(other.lideranca))
			return false;
		return true;
	}
	
}
