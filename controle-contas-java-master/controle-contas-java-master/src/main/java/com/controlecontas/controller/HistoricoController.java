package com.controlecontas.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.controlecontas.model.Conta;
import com.controlecontas.model.Historico;
import com.controlecontas.service.ContaService;
import com.controlecontas.service.HistoricoService;

@Controller
public class HistoricoController {

	private static final String HISTORICOS = "historicos";
	private static final String HISTORICO = "historico";
	private static final String CONTASLIST = "contas";
	private static final String TRANSFERENCIA = "TRANSFERENCIA";
	private static final String APORTE = "APORTE";
	private static final String CONTAMATRIZ = "MATRIZ";
	private static final String CONTAFILIAL = "FILIAL";

	private static final String TRANSFERIDO = "TRANSFERIDO";
	private static final String ESTORNADO = "ESTORNADO";

	@Autowired
	private HistoricoService service;

	@Autowired
	private ContaService contaService;

	@GetMapping("historico/historicos")
	public ModelAndView telaInicialHistoricos() {

		ModelAndView mv = new ModelAndView("/historicos");
		mv.addObject(HISTORICOS, buscarTodosHistoricosComContas());
		mv.addObject(HISTORICO, new Historico());

		return mv;
	}

	@GetMapping("/addHistorico")
	public ModelAndView addHistorico(Historico historico) {

		ModelAndView mv = new ModelAndView("/gerenciarHistorico");
		mv.addObject(HISTORICO, historico);
		mv.addObject(CONTASLIST, buscarTodasContas());

		return mv;
	}

	@GetMapping("/estornarTransferencia")
	public ModelAndView abrirEstorno(Historico historico) {

		ModelAndView mv = new ModelAndView("/estornarTransferencia");
		mv.addObject(HISTORICO, historico);
		mv.addObject(CONTASLIST, buscarTodasContas());

		return mv;
	}

	@GetMapping("/editHistorico/{id}")
	public ModelAndView editHistorico(@PathVariable("id") Long id) {

		Historico historico = service.buscarHistoricoPorId(id);
		if (historico.getContaOrigem() != null) {
			historico.setNomeContaOrigem(service.buscarContaPorId(historico.getContaOrigem()).getNome());
		}
		if (historico.getContaDestino() != null) {
			historico.setNomeContaDestino(service.buscarContaPorId(historico.getContaDestino()).getNome());
		}

		return abrirEstorno(historico);
	}

	public List<Conta> buscarTodasContas() {
		return contaService.buscarTodasContas();
	}

	public List<Historico> buscarTodosHistoricosComContas() {

		List<Historico> historicos = service.buscarTodosHistoricosComContas();

		for (Historico historico : historicos) {
			historico.setPodeDeletar(true);
			if (historico.getStatus().equals(ESTORNADO)) {
				historico.setPodeDeletar(false);
			}

			if (service.buscarHistoricoPorStatusContas(ESTORNADO, historico.getContaOrigem(),
					historico.getContaDestino()) != null) {
				historico.setPodeDeletar(false);
			}
		}
		return service.buscarTodosHistoricosComContas();
	}

	@PostMapping("/salvarHistorico")
	public ModelAndView salvarHistorico(@Valid Historico historico, BindingResult result) {

		validarTransferencia(result, historico);

		if (result.hasErrors()) {
			return addHistorico(historico);
		}
		historico.setStatus(TRANSFERIDO);
		service.saveHistorico(historico);
		realizarTransferencia(historico);

		return telaInicialHistoricos();
	}

	@PostMapping("/estornarHistorico")
	public ModelAndView estornarHistorico(@Valid Historico historicoParam, BindingResult result) {

		Historico historico = service.buscarHistoricoPorId(historicoParam.getId());

		validarEstorno(result, historicoParam);

		if (result.hasErrors()) {
			return abrirEstorno(historicoParam);
		}

		Historico historicoNovo = copiaHistorico(historico);

		realizarEstorno(historico);
		service.saveHistorico(historicoNovo);

		return telaInicialHistoricos();
	}

	public void realizarEstorno(Historico historico) {

		Conta contaOrigem = service.buscarContaPorId(historico.getContaOrigem());
		Conta contaDestino = service.buscarContaPorId(historico.getContaDestino());

		contaOrigem.setSaldo(contaOrigem.getSaldo() + historico.getValor());
		contaDestino.setSaldo(contaDestino.getSaldo() - historico.getValor());

		contaService.saveConta(contaOrigem);
		contaService.saveConta(contaDestino);
	}

	public void validarEstorno(BindingResult result, Historico historicoEstorno) {

		Historico historico = service.buscarHistoricoPorId(historicoEstorno.getId());

		if (historico.getTipoTransacao().equals(APORTE)) {

			if ("".equals(historico.getAporte()) || historico.getAporte() == null) {
				result.addError(new ObjectError("aporteinvalido", "O código de Aporte informado está incorreto."));
			} else if (service.buscarHistoricoPorAporteContas(historicoEstorno.getAporte(), historico.getContaOrigem(),
					historico.getContaDestino()) == null) {
				result.addError(new ObjectError("aporteinvalido", "O código de Aporte informado está incorreto."));
			}
		}
	}

	public void validarTransferencia(BindingResult result, Historico historico) {

		if (historico.getValor() == null || historico.getValor() == 0) {
			result.addError(new ObjectError("informevalor", "Informe o Valor da operação."));
		}

		if (historico.getContaOrigem() == null) {
			result.addError(new ObjectError("informecontaorigem", "Informe a Conta de Origem."));
		} else if (historico.getContaDestino() == null) {
			result.addError(new ObjectError("informecontadestino", "Informe a Conta de Destino."));
		} else {
			Conta contaOrigem = service.buscarContaPorId(historico.getContaOrigem());
			Conta contaDestino = service.buscarContaPorId(historico.getContaDestino());

			if (contaOrigem.getSaldo() < historico.getValor()) {

				result.addError(new ObjectError("aporteexistente",
						"O valor informado é mais alto do que o existente na conta de origem."));
			}

			if (historico.getTipoTransacao().equals(TRANSFERENCIA) && contaOrigem.getTipoConta().equals(CONTAFILIAL)
					&& !contaOrigem.getContaPai().equals(contaDestino.getContaPai())) {

				result.addError(new ObjectError("transferenciaarvorediferente",
						"Só podem ser realizadas Transferências entre contas da mesma Matriz."));
			}

			if (historico.getTipoTransacao().equals(APORTE)) {

				if (contaOrigem.getTipoConta().equals(CONTAMATRIZ)) {
					result.addError(new ObjectError("transferenciaarvorediferente",
							"Só podem ser realizados Aportes entre contas da mesma Matriz."));

				} else if (contaOrigem.getTipoConta().equals(CONTAFILIAL)
						&& !contaOrigem.getContaPai().equals(contaDestino.getId())) {
					result.addError(new ObjectError("transferenciaarvorediferente",
							"Só podem ser realizados Aportes entre contas da mesma Matriz."));
				}

			}

			if (historico.getTipoTransacao().equals(APORTE) && "".equals(historico.getAporte())) {
				result.addError(new ObjectError("aporteinvalido",
						"Para este tipo de transação, deve ser informado o código de Aporte."));
			}

			if (historico.getTipoTransacao().equals(APORTE)
					&& service.buscarHistoricoPorAporte(historico.getAporte()) != null) {

				result.addError(new ObjectError("aporteexistente", "Já existe um aporte com o código informado."));

			}

			if (historico.getTipoTransacao().equals(TRANSFERENCIA)
					&& service.buscarContaPorId(historico.getContaDestino()).getTipoConta().equals(CONTAMATRIZ)) {

				result.addError(new ObjectError("transferenciaparamatriz",
						"Não podem ser realizadas transferências para uma conta Matriz, somente Aportes."));
			}

			if (historico.getContaDestino().equals(historico.getContaOrigem())) {
				result.addError(new ObjectError("transferenciamesmaconta",
						"Não podem ser realizadas transferências entre a mesma conta."));
			}
		}

	}

	public void realizarTransferencia(Historico historico) {

		Conta contaOrigem = service.buscarContaPorId(historico.getContaOrigem());
		Conta contaDestino = service.buscarContaPorId(historico.getContaDestino());

		contaOrigem.setSaldo(contaOrigem.getSaldo() - historico.getValor());
		contaDestino.setSaldo(contaDestino.getSaldo() + historico.getValor());

		contaService.saveConta(contaOrigem);
		contaService.saveConta(contaDestino);
	}

	@PostMapping("/buscaHistoricos")
	public ModelAndView buscaHistoricos(Historico historico, BindingResult result) {

		ModelAndView mv = new ModelAndView("/contas");
		mv.addObject(HISTORICOS, service.buscaHistoricoPorParametros(historico));
		mv.addObject(HISTORICO, new Conta());

		return mv;
	}

	public Historico copiaHistorico(Historico historico) {

		Historico historicoNovo = new Historico();

		historicoNovo.setAporte(historico.getAporte());
		historicoNovo.setContaDestino(historico.getContaDestino());
		historicoNovo.setContaOrigem(historico.getContaOrigem());
		historicoNovo.setDataTransacao(new Date());
		historicoNovo.setStatus(ESTORNADO);
		historicoNovo.setTipoTransacao(historico.getTipoTransacao());
		historicoNovo.setValor(historico.getValor());

		return historicoNovo;
	}

}
