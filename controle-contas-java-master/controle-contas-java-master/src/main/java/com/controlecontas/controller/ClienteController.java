package com.controlecontas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.controlecontas.model.Cliente;
import com.controlecontas.service.ClienteService;

@Controller
public class ClienteController {

	private static final String CLIENTES = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String TIPOCLIENTE = "tipocliente";

	@Autowired
	private ClienteService service;

	@GetMapping("cliente/clientes")
	public ModelAndView telaInicialClientes() {

		ModelAndView mv = new ModelAndView("/clientes");
		List<Cliente> clientes = buscarTodosClientes();
		mv.addObject(CLIENTES, clientes);
		mv.addObject(CLIENTE, new Cliente());

		return mv;
	}

	@GetMapping("/addCliente")
	public ModelAndView addCliente(Cliente cliente) {

		ModelAndView mv = new ModelAndView("/gerenciarCliente");
		mv.addObject(CLIENTE, cliente);
		List<String> tiposCliente = new ArrayList<>();
		tiposCliente.add("Pessoa Jurídica");
		tiposCliente.add("Pessoa Física");
		mv.addObject(TIPOCLIENTE, tiposCliente);

		return mv;
	}

	@GetMapping("/editCliente/{id}")
	public ModelAndView editCliente(@PathVariable("id") Long id) {
		return addCliente(service.buscarClientePorId(id));
	}

	@GetMapping("/deleteCliente/{id}")
	public ModelAndView deleteCliente(@PathVariable("id") Long id) {

		service.deleteCliente(id);

		return telaInicialClientes();
	}

	public List<Cliente> buscarTodosClientes() {
		return service.buscarTodosClientes();
	}

	@PostMapping("/salvarCliente")
	public ModelAndView salvarCliente(@Valid Cliente cliente, BindingResult result) {

		if (cliente.getTipoCliente().equals("PJ")) {
			cliente.setData(new Date());
		}

//		if (result.hasErrors()) {
//			return addCliente(cliente);
//		}
		service.saveCliente(cliente);

		return telaInicialClientes();
	}

	@PostMapping("/buscaClientes")
	public ModelAndView buscaClientes(Cliente cliente, BindingResult result) {

		ModelAndView mv = new ModelAndView("/clientes");
		mv.addObject(CLIENTES, service.buscaClientePorParametros(cliente));
		mv.addObject(CLIENTE, new Cliente());

		return mv;
	}

}
