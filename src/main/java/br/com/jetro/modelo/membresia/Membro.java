package br.com.jetro.modelo.membresia;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.jetro.util.Util;

@Entity
@Table(name="membro")
@NamedQueries(
		{@NamedQuery(name="listarAniversariantesPorMes",
			query="SELECT m FROM Membro m WHERE  EXTRACT(MONTH FROM m.dadosComplementares.dataNascimento) = :mes")
		})
public class Membro implements Serializable, Comparable<Membro>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_membro", sequenceName = "seq_membro", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_membro")
	private Long id;
	
	@NotNull
	@Column(name="data_cadastro", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="membro", fetch=FetchType.LAZY)
	private Identificacao identificacao;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="membro", fetch=FetchType.LAZY)
	private DadosComplementares dadosComplementares;
	
	@Embedded
	private Endereco endereco;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="membro", fetch=FetchType.LAZY)
	private DadosEclesiastico dadosEclesiastico;
	
	private String observacoes;
	
	public Membro() {
		this.identificacao = new Identificacao(this);
		this.endereco = new Endereco();
		this.dadosComplementares = new DadosComplementares(this);
		this.dadosEclesiastico = new DadosEclesiastico(this);
	}
	
	@Override
	public int compareTo(Membro o) {
		return this.identificacao.getNome().compareTo(o.getIdentificacao().getNome());
	}
	
	@PrePersist
	private void prePersistpreUpdate(){
		
		this.endereco.setCep(Util.removerFormatacao(this.endereco.getCep()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Identificacao getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(Identificacao identificacao) {
		this.identificacao = identificacao;
	}

	public DadosComplementares getDadosComplementares() {
		return dadosComplementares;
	}

	public void setDadosComplementares(DadosComplementares dadosComplementares) {
		this.dadosComplementares = dadosComplementares;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DadosEclesiastico getDadosEclesiastico() {
		return dadosEclesiastico;
	}

	public void setDadosEclesiastico(DadosEclesiastico dadosEclesiastico) {
		this.dadosEclesiastico = dadosEclesiastico;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
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
		if (!(obj instanceof Membro))
			return false;
		Membro other = (Membro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
