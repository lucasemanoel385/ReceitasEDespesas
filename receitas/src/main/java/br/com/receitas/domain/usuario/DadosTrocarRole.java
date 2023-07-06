package br.com.receitas.domain.usuario;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DadosTrocarRole(
		@NotEmpty(message = "Escolha o email valido para trocar de role")
		String login, 
		@NotNull(message = "1 pra ROLE_USER e 2 pra ROLE_ADMIN")
		Long id) {

}
