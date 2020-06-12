package com.controlecontas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "tipocliente", length = 50)
	private String tipoCliente;

	@Column(name = "data")
	private Date data;

	@Column(name = "cpfcnpj", length = 50)
	private String cpfCnpj;

	@Column(name = "nomefantasia", length = 50)
	private String nomeFantasia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

}
