package br.com.receitas.domain.receitas;

import java.time.LocalDate;

public record ListarReceitas(Long id , String descricao, Float valor, LocalDate data) {
	
	public ListarReceitas(Receitas receita) {
		this(receita.getId() ,receita.getDescricao(), receita.getValor(), receita.getData());
	}

}
