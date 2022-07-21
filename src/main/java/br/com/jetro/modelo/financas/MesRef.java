package br.com.jetro.modelo.financas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="MesRef")
@NamedQueries(
		{@NamedQuery(name="mesAtual", 
			query="SELECT m FROM MesRef m WHERE m.dataDesativacao IS NULL"),
		@NamedQuery(name="consultaMesRefPorData", 
			query="SELECT m FROM MesRef m WHERE m.data BETWEEN :dataInicio AND :dataFim")
		})
public class MesRef implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8024225673460328960L;
	
	@Id
	@Column(name="id", nullable=false, length=18)
	@SequenceGenerator(name="seq_mesref", sequenceName = "seq_mesref", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_mesref")
	private Long id;
	
	@NotNull
	@Column(name="dataRef", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@NotNull
	@Column(name="dataAtivacao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtivacao;
	
	@Column(name="dataDesativacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDesativacao;
	
	@Column(name="valorSaldo", precision=10, scale=2, nullable=false)	
	private Double valorSaldo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}
	
	public Double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((dataAtivacao == null) ? 0 : dataAtivacao.hashCode());
		result = prime * result
				+ ((dataDesativacao == null) ? 0 : dataDesativacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((valorSaldo == null) ? 0 : valorSaldo.hashCode());
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
		MesRef other = (MesRef) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dataAtivacao == null) {
			if (other.dataAtivacao != null)
				return false;
		} else if (!dataAtivacao.equals(other.dataAtivacao))
			return false;
		if (dataDesativacao == null) {
			if (other.dataDesativacao != null)
				return false;
		} else if (!dataDesativacao.equals(other.dataDesativacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (valorSaldo == null) {
			if (other.valorSaldo != null)
				return false;
		} else if (!valorSaldo.equals(other.valorSaldo))
			return false;
		return true;
	}

	
}
