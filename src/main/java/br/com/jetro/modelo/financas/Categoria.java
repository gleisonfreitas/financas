package br.com.jetro.modelo.financas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.jetro.enums.TipoLancamento;

@Entity
@Table(name="Categoria")
public class Categoria implements Serializable, Comparable<Categoria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 780189486692528076L;

	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_categoria", sequenceName = "seq_categoria", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_categoria")
	private Long id;
	
	@NotNull
	@NotEmpty
	@Column(name="descricao", length=50, nullable=false)
	private String descricao;
	
	@NotNull
	@Column(name="tipo", nullable=false)
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;
	
	@Override
	public int compareTo(Categoria o) {
		return this.descricao.compareTo(o.descricao);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	
	public boolean isCredito(){
		 return this.tipoLancamento.equals(TipoLancamento.CREDITO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((tipoLancamento == null) ? 0 : tipoLancamento.hashCode());
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
		Categoria other = (Categoria) obj;
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
		if (tipoLancamento != other.tipoLancamento)
			return false;
		return true;
	}
}
