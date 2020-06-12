package com.controlecontas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlecontas.model.Cliente;
import com.controlecontas.model.Conta;
import com.controlecontas.repository.ClienteRepository;
import com.controlecontas.repository.ContaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Conta> buscarTodasContas() {
		return contaRepository.buscaContasAtivas();
	}

	public List<Conta> adicionaClienteContaPai(List<Conta> contas) {

		List<Conta> retorno = new ArrayList<>();
		Cliente cliente;
		for (Conta conta : contas) {
			cliente = this.buscarClientePorId(conta.getCliente());
			if (conta.getCliente() != null) {
				if(cliente != null) {
					conta.setNomeCliente(cliente.getNome());
				}
			}
			if (conta.getContaPai() != null) {
				conta.setNomeContaPai(this.buscarContaPorId(conta.getContaPai()).getNome());
			}

			retorno.add(conta);
		}

		return retorno;
	}

	public List<Conta> buscarTodasContasComClienteEContaPai() {
		return adicionaClienteContaPai(contaRepository.findAll());
	}

	public Conta buscarContaPorId(Long id) {
		return contaRepository.findOne(id);
	}

	public void deleteConta(Long id) {
		contaRepository.delete(id);
	}

	public Conta saveConta(Conta conta) {
		return contaRepository.saveAndFlush(conta);
	}

	public List<Conta> buscaContaPorParametros(Conta conta) {

		List<Conta> retorno = new ArrayList<>();
		if (conta.getId() != null && "".equals(conta.getNumero())) {

			Conta contaUnica = contaRepository.findOne(conta.getId());
			if (contaUnica != null) {
				retorno.add(contaRepository.findOne(contaUnica.getId()));
			}
		} else if (conta.getId() != null && !"".equals(conta.getNumero())) {
			retorno = contaRepository.buscaContaPorNumeroId(conta.getNumero(), conta.getId());
		} else if (conta.getId() == null && !"".equals(conta.getNumero())) {
			retorno = contaRepository.buscaContaPorNumero(conta.getNumero());
		} else {
			retorno = contaRepository.findAll();
		}

		return adicionaClienteContaPai(retorno);
	}

	public Cliente buscarClientePorId(Long id) {
		return clienteRepository.findOne(id);
	}

}
