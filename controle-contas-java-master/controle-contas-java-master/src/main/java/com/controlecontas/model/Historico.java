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

@Entity(name = "historico")
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_historico", sequenceName = "seq_historico")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico")
	private Long id;

	@Column(name = "aporte")
	private String aporte;

	@Column(name = "status")
	private String status;

	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "tipoTransacao")
	private String tipoTransacao;

	@Column(name = "datatransacao")
	private Date dataTransacao;

	@Column(name = "contaOrigem", nullable = false)
	private Long contaOrigem;

	@Column(name = "contaDestino", nullable = false)
	private Long contaDestino;

	@Transient
	private String nomeContaOrigem;

	@Transient
	private String nomeContaDestino;

	@Transient
	private boolean podeDeletar;

	public boolean isPodeDeletar() {
		return podeDeletar;
	}

	public void setPodeDeletar(boolean podeDeletar) {
		this.podeDeletar = podeDeletar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAporte() {
		return aporte;
	}

	public void setAporte(String aporte) {
		this.aporte = aporte;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public Long getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Long contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Long getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Long contaDestino) {
		this.contaDestino = contaDestino;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNomeContaOrigem() {
		return nomeContaOrigem;
	}

	public void setNomeContaOrigem(String nomeContaOrigem) {
		this.nomeContaOrigem = nomeContaOrigem;
	}

	public String getNomeContaDestino() {
		return nomeContaDestino;
	}

	public void setNomeContaDestino(String nomeContaDestino) {
		this.nomeContaDestino = nomeContaDestino;
	}

}
