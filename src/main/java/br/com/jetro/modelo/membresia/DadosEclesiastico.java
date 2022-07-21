package br.com.jetro.modelo.membresia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="dados_Eclesiastico")
public class DadosEclesiastico implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_dados_eclesiastico", sequenceName = "seq_dados_eclesiastico", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_dados_eclesiastico")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_membro")
	private Membro membro;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_batismo")
	private Date dataBatismo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="dadosEclesiastico", fetch=FetchType.LAZY)
	private List<HistoricoFuncao> funcoes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="dadosEclesiastico", fetch=FetchType.LAZY)
	private List<HistoricoLideranca> liderancas;
	
	public DadosEclesiastico() {
		super();
	}
	
	public DadosEclesiastico(Membro membro) {
		this.membro = membro;
		setFuncoes(new ArrayList<HistoricoFuncao>());
		setLiderancas(new ArrayList<HistoricoLideranca>());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public Date getDataBatismo() {
		return dataBatismo;
	}

	public void setDataBatismo(Date dataBatismo) {
		this.dataBatismo = dataBatismo;
	}

	public List<HistoricoFuncao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<HistoricoFuncao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<HistoricoLideranca> getLiderancas() {
		return liderancas;
	}

	public void setLiderancas(List<HistoricoLideranca> liderancas) {
		this.liderancas = liderancas;
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
		if (!(obj instanceof DadosEclesiastico))
			return false;
		DadosEclesiastico other = (DadosEclesiastico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
