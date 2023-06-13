package br.com.receitas.domain.despesas;

import java.time.LocalDate;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroEAtualizarDespesas(
		
		@NotEmpty(message = "campo descrição está invalido")
		String descricao,
		@NotNull(message = "campo valor está invalido. Exemplo: (15.23)")
		Float valor,
		@DateTimeFormat
		@NotNull(message = "campo data está invalido. Exemplo: (year-mouth-day)")
		LocalDate data,
		CategoriaDespesas categoria) {

}
