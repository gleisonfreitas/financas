package br.com.jetro.modelo.membresia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="identificacao")
public class Identificacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=18, nullable=false)
	@SequenceGenerator(name="seq_identificacao", sequenceName = "seq_identificacao", initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_identificacao")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_membro")
	private Membro membro;
	
	@NotBlank(message="é obrigatório.")
	private String nome;
	
	@Email(message="inválido")
	private String email;
	
	private String foto;
	
	public Identificacao() {
		super();
	}
	
	public Identificacao(Membro membro){
		this.membro = membro;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
