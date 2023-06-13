package br.com.receitas.domain.despesas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesasRepository extends JpaRepository<Despesas, Long>{

	Page<Despesas> findByDescricaoContains(String descricao, Pageable page);

	@Query(value = "select * from despesas where extract(month from data) = :mes and extract(year from data) = :ano", nativeQuery = true )
	Page<Despesas> findAllByAnoAndMes(int ano, int mes, Pageable page);

}
