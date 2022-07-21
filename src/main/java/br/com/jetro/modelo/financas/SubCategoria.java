package br.com.jetro.modelo.financas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SubCategoria")
public class SubCategoria implements Serializable, Comparable<SubCategoria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3466733245940316594L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_subcategoria", sequenceName = "seq_subcategoria", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_subcategoria")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="categoria_id", nullable=false)
	private Categoria categoria;
	
	@Column(name="descricao", length=50, nullable=false)
	private String descricao;
	
	@Override
	public int compareTo(SubCategoria o) {
		
		return this.descricao != null ? this.descricao.compareTo(o.descricao) : -1;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
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
		SubCategoria other = (SubCategoria) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
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
		return true;
	}

}
