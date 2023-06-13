package br.com.receitas.domain.validacoes;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.receitas.domain.despesas.DadosCadastroEAtualizarDespesas;
import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.infra.exceptions.ValidacaoException;

@Component
public class ValidacaoCamposObrigatorios implements ValidadorCampoECadastroDaDescricaoMesmoMes{

	@Override
	public void validar(DadosCadastroEAtualizarReceita dados) throws ValidacaoException {
		
		if (LocalDate.now().isAfter(dados.data())) {
			throw new ValidacaoException("Não pode adicionar data passada");
		}
	}

	@Override
	public void validar(DadosCadastroEAtualizarDespesas dados) throws ValidacaoException {
		if (LocalDate.now().isAfter(dados.data())) {
			throw new ValidacaoException("Não pode adicionar data passada");
		}
		
	}
	
	

}
