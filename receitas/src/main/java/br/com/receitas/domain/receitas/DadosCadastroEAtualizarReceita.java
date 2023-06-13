package br.com.receitas.domain.receitas;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroEAtualizarReceita(
		@NotEmpty(message = "campo descrição está invalido")
		String descricao, 
		@NotNull(message = "campo valor está invalido. Exemplo: (15.23)")
		Float valor, 
		@NotNull(message = "campo data está invalido. Exemplo: (year-mouth-day)")
		LocalDate data) {

}
