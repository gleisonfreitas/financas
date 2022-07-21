package br.com.jetro.modelo.financas;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.jetro.validation.DecimalPositivo;

@Entity
@Table(name="Lancamento")
@NamedQueries(
		{@NamedQuery(
				name="lancamentoPorTipoEMesRef", 
				query="SELECT l FROM Lancamento l WHERE l.subCategoria.categoria.tipoLancamento = :tipo AND l.mesRef.id = :codigoMesRef order by l.subCategoria.categoria.descricao"),
		@NamedQuery(
				name="totalPorTipoEMesRef", 
				query="SELECT SUM(l.valor) FROM Lancamento l WHERE l.subCategoria.categoria.tipoLancamento = :tipo AND l.mesRef.id = :codigoMesRef"),
		@NamedQuery(
				name="lancamentoAoDiaPorTipoEMesRef", 
				query="SELECT l.data, SUM(l.valor) FROM Lancamento l WHERE l.subCategoria.categoria.tipoLancamento = :tipo AND l.mesRef.id = :codigoMesRef GROUP BY l.data"),
		@NamedQuery(
				name="totalLancamentoPorSubCategoria", 
				query="SELECT l.subCategoria.descricao, SUM(l.valor) FROM Lancamento l WHERE l.subCategoria.categoria.tipoLancamento = :tipo AND l.mesRef.id = :codigoMesRef GROUP BY l.subCategoria.descricao"),
		@NamedQuery(
				name="lancamentoPorMesRef", 
				query="SELECT l FROM Lancamento l WHERE l.mesRef.id = :codigoMesRef ORDER BY l.data"),
		@NamedQuery(
				name="lancamentoPorAno",
				query="SELECT l FROM Lancamento l WHERE l.data BETWEEN :inicio AND :fim"),
		@NamedQuery(
				name="lancamentoPorAnoSubCategoria",
				query="SELECT l FROM Lancamento l WHERE l.data BETWEEN :inicio AND :fim AND l.subCategoria.id = :subCategoria")})
public class Lancamento implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7356341280142261176L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_lancamento", sequenceName = "seq_lancamento", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_lancamento")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="mesRef", nullable=false)
	private MesRef mesRef;
	
	@NotNull
	@Column(name="data", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date data;

	@NotEmpty
	@Column(name="descricao", length=255, nullable=false)
	private String descricao;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="subCategoria", nullable= false)
	private SubCategoria subCategoria;
	
	@DecimalPositivo
	@Column(name="valor", precision=10, scale=2, nullable=false)	
	private Double valor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MesRef getMesRef() {
		return mesRef;
	}

	public void setMesRef(MesRef mesRef) {
		this.mesRef = mesRef;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mesRef == null) ? 0 : mesRef.hashCode());
		result = prime * result
				+ ((subCategoria == null) ? 0 : subCategoria.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Lancamento other = (Lancamento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mesRef == null) {
			if (other.mesRef != null)
				return false;
		} else if (!mesRef.equals(other.mesRef))
			return false;
		if (subCategoria == null) {
			if (other.subCategoria != null)
				return false;
		} else if (!subCategoria.equals(other.subCategoria))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
