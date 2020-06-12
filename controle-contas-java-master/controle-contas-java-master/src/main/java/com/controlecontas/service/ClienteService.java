package com.controlecontas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlecontas.model.Cliente;
import com.controlecontas.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> buscarTodosClientes() {
		return clienteRepository.findAll();
	}

	public Cliente buscarClientePorId(Long id) {
		return clienteRepository.findOne(id);
	}

	public void deleteCliente(Long id) {
		clienteRepository.delete(id);
	}

	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.saveAndFlush(cliente);
	}

	public List<Cliente> buscaClientePorParametros(Cliente cliente) {

		if (cliente.getId() != null && "".equals(cliente.getNome())) {

			List<Cliente> retorno = new ArrayList<>();
			Cliente clienteUnico = clienteRepository.findOne(cliente.getId());
			if (clienteUnico != null) {
				retorno.add(clienteRepository.findOne(cliente.getId()));
			}
			return retorno;
		} else if (cliente.getId() != null && !"".equals(cliente.getNome())) {
			return clienteRepository.buscaClientePorNomeId(cliente.getNome(), cliente.getId());
		} else if (cliente.getId() == null && !"".equals(cliente.getNome())) {
			return clienteRepository.buscaClientePorNome(cliente.getNome());
		} else {
			return clienteRepository.findAll();
		}
	}

}
