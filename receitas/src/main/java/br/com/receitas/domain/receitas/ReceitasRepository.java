package br.com.receitas.domain.receitas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceitasRepository extends JpaRepository<Receitas, Long>{

	Page<Receitas> findByDescricaoContains(String descricao, Pageable page);

	@Query(value = "select * from receitas where extract(month from data) = :mes and extract(year from data) = :ano", nativeQuery = true )
	Page<Receitas> findAllByAnoAndMes(int ano, int mes, Pageable page);


}
