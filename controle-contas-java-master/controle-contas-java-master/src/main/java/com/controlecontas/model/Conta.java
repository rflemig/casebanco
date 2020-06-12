package com.controlecontas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "conta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_conta", sequenceName = "seq_conta")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta")
	private Long id;

	@Column(name = "nome", nullable = false, length = 150)
	@NotBlank(message = "Informe o Nome do Banco.")
	private String nome;

	@Column(name = "numero", nullable = false, length = 150)
	@NotBlank(message = "Informe o Número da Conta.")
	private String numero;

	@Column(name = "saldo", nullable = false)
	private Double saldo;

	@Column(name = "tipoconta")
	private String tipoConta;

	@Column(name = "situacao", nullable = false)
	@NotBlank(message = "Informe a Situação da Conta.")
	private String situacao;

	@Column(name = "datacriacao")
	private Date dataCriacao;

	@Column(name = "contapai", nullable = true)
	private Long contaPai;

	@Column(name = "cliente", nullable = false)
	private Long cliente;

	@Transient
	private String nomeCliente;
	
	@Transient
	private String nomeContaPai;
	
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getContaPai() {
		return contaPai;
	}

	public void setContaPai(Long contaPai) {
		this.contaPai = contaPai;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeContaPai() {
		return nomeContaPai;
	}

	public void setNomeContaPai(String nomeContaPai) {
		this.nomeContaPai = nomeContaPai;
	}

}
