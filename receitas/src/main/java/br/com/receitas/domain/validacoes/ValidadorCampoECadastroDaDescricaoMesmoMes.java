package br.com.receitas.domain.validacoes;

import br.com.receitas.domain.despesas.DadosCadastroEAtualizarDespesas;
import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.infra.exceptions.ValidacaoException;

public interface ValidadorCampoECadastroDaDescricaoMesmoMes {

	void validar(DadosCadastroEAtualizarReceita dados) throws ValidacaoException;
	
	void validar(DadosCadastroEAtualizarDespesas dados) throws ValidacaoException;
}
