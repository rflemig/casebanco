package com.controlecontas.controller;

import java.util.ArrayList;
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
import com.controlecontas.service.ClienteService;
import com.controlecontas.service.ContaService;

@Controller
public class ContaController {

	private static final String CONTAS = "contas";
	private static final String CONTA = "conta";
	private static final String CLIENTESLIST = "clientes";
	private static final String MATRIZ = "MATRIZ";
	private static final String FILIAL = "FILIAL";

	@Autowired
	private ContaService service;

	@Autowired
	private ClienteService clienteService;

	@GetMapping("conta/contas")
	public ModelAndView telaInicialContas() {

		ModelAndView mv = new ModelAndView("/contas");
		mv.addObject(CONTAS, buscarTodasContasComClienteEContaPai());
		mv.addObject(CONTA, new Conta());

		return mv;
	}

	@GetMapping("/addConta")
	public ModelAndView addConta(Conta conta) {

		ModelAndView mv = new ModelAndView("/gerenciarConta");
		mv.addObject(CONTA, conta);
		mv.addObject(CONTAS, buscarTodasContas());
		List<String> tiposConta = new ArrayList<>();
		tiposConta.add("Matriz");
		tiposConta.add("Filial");
		mv.addObject(CLIENTESLIST, clienteService.buscarTodosClientes());

		return mv;
	}

	@GetMapping("/editConta/{id}")
	public ModelAndView editConta(@PathVariable("id") Long id) {

		Conta conta = service.buscarContaPorId(id);
		if (conta.getCliente() != null) {
			conta.setNomeCliente(service.buscarClientePorId(conta.getCliente()).getNome());
		}
		if (conta.getContaPai() != null) {
			conta.setNomeContaPai(service.buscarContaPorId(conta.getContaPai()).getNome());
		}

		return addConta(conta);
	}

	@GetMapping("/deleteConta/{id}")
	public ModelAndView deleteConta(@PathVariable("id") Long id) {

		Conta conta = service.buscarContaPorId(id);
		conta.setSituacao("INATIVA");
		service.saveConta(conta);

		return telaInicialContas();
	}

	public List<Conta> buscarTodasContas() {
		return service.buscarTodasContas();
	}

	public List<Conta> buscarTodasContasComClienteEContaPai() {
		return service.buscarTodasContasComClienteEContaPai();
	}

	@PostMapping("/salvarConta")
	public ModelAndView salvarConta(@Valid Conta conta, BindingResult result) {

		validarConta(result, conta);

		if (result.hasErrors()) {
			return addConta(conta);
		}
		conta.setDataCriacao(new Date());

		if (conta.getTipoConta().equals(MATRIZ)) {
			conta.setContaPai(null);
		}

		service.saveConta(conta);

		return telaInicialContas();
	}

	public void validarConta(BindingResult result, Conta conta) {
		if(conta.getSaldo() == null) {
			result.addError(new ObjectError("informesaldo", "Informe o Saldo da Conta."));
		}
		
		if(conta.getCliente() == null) {
			result.addError(new ObjectError("informecliente", "Informe o Cliente da Conta."));
		}
		if (conta.getTipoConta().equals(FILIAL) && conta.getContaPai() == null) {
			result.addError(new ObjectError("filialsempai", "Contas Filiais devem ter uma Conta Pai associada."));
		}

	}

	@PostMapping("/buscaContas")
	public ModelAndView buscaContas(Conta conta, BindingResult result) {

		ModelAndView mv = new ModelAndView("/contas");
		mv.addObject(CONTAS, service.buscaContaPorParametros(conta));
		mv.addObject(CONTA, new Conta());

		return mv;
	}

}
