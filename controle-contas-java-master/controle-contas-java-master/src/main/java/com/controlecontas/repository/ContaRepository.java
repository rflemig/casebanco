package com.controlecontas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.controlecontas.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	@Query("From conta where numero like %?1% and id = ?2")
	public List<Conta> buscaContaPorNumeroId(String numero, Long id);

	@Query("From conta where situacao like 'ATIVA'")
	public List<Conta> buscaContasAtivas();
	
	@Query("From conta where nome like %?1%")
	public List<Conta> buscaContaPorNumero(String numero);
}
