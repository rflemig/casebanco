package com.controlecontas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.controlecontas.model.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {

	@Query("From historico where status like %?1% and id = ?2")
	public List<Historico> buscaHisotricoPorStatusId(String numero, Long id);

	@Query("From historico where status like %?1%")
	public List<Historico> buscaHistoricoPorStatus(String numero);

	@Query("From historico where aporte like ?1")
	public Historico buscarHistoricoPorAporte(String aporte);
	
	@Query("From historico where aporte like ?1 and contaOrigem = ?2 and contaDestino = ?3")
	public Historico buscarHistoricoPorAporteContas(String aporte, Long contaOrigem, Long contaDestino);
	
	@Query("From historico where status like ?1 and contaOrigem = ?2 and contaDestino = ?3")
	public Historico buscarHistoricoPorStatusContas(String aporte, Long contaOrigem, Long contaDestino);
}
