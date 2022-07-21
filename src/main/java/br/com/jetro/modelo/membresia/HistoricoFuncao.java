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
@Table(name="historico_funcao")
@NamedQueries(
		{@NamedQuery(name="excluirHistoricoFuncao",query="DELETE FROM HistoricoFuncao h WHERE h.dadosEclesiastico.id = :idDadosEclesiastico AND h.id NOT IN (:ids))"),
		 @NamedQuery(name="excluirTodosHistoricoFuncao",query="DELETE FROM HistoricoFuncao h WHERE h.dadosEclesiastico.id = :idDadosEclesiastico")})
public class HistoricoFuncao implements Serializable, Comparable<HistoricoFuncao>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_historico_funcao", sequenceName = "seq_historico_funcao", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_historico_funcao")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_dados_eclesiastico")
	private DadosEclesiastico dadosEclesiastico;

	@ManyToOne
	@JoinColumn(name="id_funcao")
	private Funcao funcao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_ordenacao")
	private Date dataOrdenacao;
	
	public HistoricoFuncao() {
		super();
	}
	
	public HistoricoFuncao(DadosEclesiastico dadosEclesiastico) {
		this.dadosEclesiastico = dadosEclesiastico;
	}
	
	@Override
	public int compareTo(HistoricoFuncao o) {
		return this.dataOrdenacao.compareTo(o.dataOrdenacao);
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

	public Date getDataOrdenacao() {
		return dataOrdenacao;
	}

	public void setDataOrdenacao(Date dataOrdenacao) {
		this.dataOrdenacao = dataOrdenacao;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		HistoricoFuncao other = (HistoricoFuncao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
