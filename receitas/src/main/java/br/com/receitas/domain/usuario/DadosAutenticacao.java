package br.com.receitas.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record DadosAutenticacao(
		@NotEmpty(message = "Campo preenchido errado ou invalido")
		String login,
		@NotEmpty(message = "Campo preenchido errado ou invalido")
		String senha) {

}
