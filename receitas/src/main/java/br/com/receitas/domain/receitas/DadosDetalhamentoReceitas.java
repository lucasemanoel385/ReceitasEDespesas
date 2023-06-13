package br.com.receitas.domain.receitas;

import java.time.LocalDate;

public record DadosDetalhamentoReceitas(Long id ,String descricao, Float valor, LocalDate data) {
	
	public DadosDetalhamentoReceitas(Receitas receita) {
		this(receita.getId() ,receita.getDescricao(), receita.getValor(), receita.getData());
	}

}
