package br.com.jetro.modelo.membresia;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.jetro.enums.EstadoCivilEnum;
import br.com.jetro.enums.NacionalidadeEnum;
import br.com.jetro.enums.SexoEnum;
import br.com.jetro.util.Util;

@Entity
@Table(name="dados_complementares")
public class DadosComplementares implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_dados_complementares", sequenceName = "seq_dados_complementares", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_dados_complementares")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_membro")
	private Membro membro;
	
	private String cpf;
	
	@NotNull(message="é obrigatório")
	@Enumerated(EnumType.STRING)
	private SexoEnum sexo;
	
	@ManyToOne
	@JoinColumn(name="id_cidade")
	private Cidade naturalidade;
	
	@Column(name="telefone_fixo")
	private String telefoneFixo;
	
	@Column(name="telefone_celular")
	private String telefoneCelular;
	
	@NotNull(message="é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name="estado_civil")
	private EstadoCivilEnum estadoCivil;
	
	@NotNull(message="é obrigatório")
	@Enumerated(EnumType.STRING)
	private NacionalidadeEnum nacionalidade;
	
	@ManyToOne
	@JoinColumn(name="id_profissao")
	private Profissao profissao;
	
	@NotNull(message="é obrigatório")
	@Column(name="data_nascimento")
	private Date dataNascimento;
	
	private String congregando;
	
	@Column(name="data_saida")
	private Date dataSaida;
	
	public DadosComplementares() {
		super();
	}
	
	public DadosComplementares(Membro membro) {
		this.membro = membro;
	}
	
	@PrePersist
	private void prePersistPreUpdate(){
		this.cpf = Util.removerFormatacao(this.cpf);
		this.telefoneFixo = Util.removerFormatacao(this.telefoneFixo);
		this.telefoneCelular = Util.removerFormatacao(this.telefoneCelular);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	public Cidade getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Cidade naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public NacionalidadeEnum getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(NacionalidadeEnum nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	
	public String getCongregando() {
		return congregando;
	}

	public void setCongregando(String congregando) {
		this.congregando = congregando;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
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
		if (!(obj instanceof DadosComplementares))
			return false;
		DadosComplementares other = (DadosComplementares) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
