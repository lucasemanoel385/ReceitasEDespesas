package br.com.receitas.domain.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.receitas.domain.despesas.DadosCadastroEAtualizarDespesas;
import br.com.receitas.domain.despesas.Despesas;
import br.com.receitas.domain.despesas.DespesasRepository;
import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.domain.receitas.Receitas;
import br.com.receitas.domain.receitas.ReceitasRepository;
import br.com.receitas.infra.exceptions.ValidacaoException;
import jakarta.validation.Valid;

@Component
public class ValidacoesCadastroEDescricaoMesmoMes implements ValidadorCampoECadastroDaDescricaoMesmoMes{
	
	@Autowired
	private ReceitasRepository receitasRepository;
	
	@Autowired
	private DespesasRepository despesasRepository;
	
	@Override
	public void validar(@Valid DadosCadastroEAtualizarReceita dados) throws ValidacaoException {
		
		var test = receitasRepository.findAll();
		
		for (Receitas receitas : test) {
			boolean treta = receitas.getDescricao().equals(dados.descricao()) && receitas.getData().getMonth().equals(dados.data().getMonth());
				if (treta) {
					throw new ValidacaoException("Não é permitido a mesma receita dentre o mesmo mês");
			}
		}
	}

	@Override
	public void validar(DadosCadastroEAtualizarDespesas dados) throws ValidacaoException {
		var test = despesasRepository.findAll();
		
		for (Despesas receitas : test) {
			boolean treta = receitas.getDescricao().equals(dados.descricao()) && receitas.getData().getMonth().equals(dados.data().getMonth());
				if (treta) {
					throw new ValidacaoException("Não é permitido a mesma receita dentre o mesmo mês");
			}
		}
		
	}
	
}
