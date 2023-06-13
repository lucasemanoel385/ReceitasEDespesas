package br.com.receitas.domain.despesas;

import java.time.LocalDate;

import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;

public record DadosDetalhamentoDespesas(String Descricao, Float valor, LocalDate data, CategoriaDespesas categoria) {

	public DadosDetalhamentoDespesas(Despesas despesa) {
		
		this(despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria());
	}
}
