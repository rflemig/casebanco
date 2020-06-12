package com.controlecontas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.controlecontas.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("From cliente where nome like %?1% and id = ?2")
	public List<Cliente> buscaClientePorNomeId(String nome, Long id);
	
	@Query("From cliente where nome like %?1%")
	public List<Cliente> buscaClientePorNome(String nome);
}
