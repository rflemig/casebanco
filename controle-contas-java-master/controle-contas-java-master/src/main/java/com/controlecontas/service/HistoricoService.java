package com.controlecontas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlecontas.model.Conta;
import com.controlecontas.model.Historico;
import com.controlecontas.repository.ContaRepository;
import com.controlecontas.repository.HistoricoRepository;

@Service
public class HistoricoService {

	@Autowired
	private HistoricoRepository historicoRepository;

	@Autowired
	private ContaRepository contaRepository;

	public List<Historico> buscarTodosHistoricosComContas() {
		return adicionaContaHistorico(historicoRepository.findAll());
	}

	public Historico buscarHistoricoPorAporte(String aporte) {
		return historicoRepository.buscarHistoricoPorAporte(aporte);
	}

	public List<Historico> adicionaContaHistorico(List<Historico> historicos) {

		List<Historico> retorno = new ArrayList<>();
		for (Historico historico : historicos) {
			if (historico.getContaOrigem() != null) {
				historico.setNomeContaOrigem(this.buscarContaPorId(historico.getContaOrigem()).getNome());
			}
			if (historico.getContaDestino() != null) {
				historico.setNomeContaDestino(this.buscarContaPorId(historico.getContaDestino()).getNome());
			}

			retorno.add(historico);
		}

		return retorno;
	}

	public Historico buscarHistoricoPorId(Long id) {
		return historicoRepository.findOne(id);
	}

	public List<Historico> buscaHistoricoPorParametros(Historico historico) {

		List<Historico> retorno = new ArrayList<>();
		if (historico.getId() != null && "".equals(historico.getStatus())) {

			Historico historicoUnico = historicoRepository.findOne(historico.getId());
			if (historicoUnico != null) {
				retorno.add(historicoRepository.findOne(historicoUnico.getId()));
			}
		} else if (historico.getId() != null && !"".equals(historico.getStatus())) {
			retorno = historicoRepository.buscaHisotricoPorStatusId(historico.getStatus(), historico.getId());
		} else if (historico.getId() == null && !"".equals(historico.getStatus())) {
			retorno = historicoRepository.buscaHistoricoPorStatus(historico.getStatus());
		} else {
			retorno = historicoRepository.findAll();
		}

		return adicionaContaHistorico(retorno);
	}

	public Historico saveHistorico(Historico historico) {
		return historicoRepository.saveAndFlush(historico);
	}

	public Conta buscarContaPorId(Long id) {
		return contaRepository.findOne(id);
	}

	public Historico buscarHistoricoPorAporteContas(String aporte, Long contaOrigem, Long contaDestino) {
		return historicoRepository.buscarHistoricoPorAporteContas(aporte, contaOrigem, contaDestino);
	}

	public Historico buscarHistoricoPorStatusContas(String status, Long contaOrigem, Long contaDestino) {
		return historicoRepository.buscarHistoricoPorStatusContas(status, contaOrigem, contaDestino);
	}

}
