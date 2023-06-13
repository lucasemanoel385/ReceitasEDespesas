package br.com.receitas.domain.despesas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.receitas.domain.despesas.DadosCadastroEAtualizarDespesas;
import br.com.receitas.domain.despesas.Despesas;
import br.com.receitas.domain.despesas.DespesasRepository;
import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;
import br.com.receitas.domain.validacoes.ValidadorCampoECadastroDaDescricaoMesmoMes;
import br.com.receitas.infra.exceptions.ValidacaoException;
import jakarta.validation.Valid;

@Service
public class DespesasService {
	
	@Autowired
	private DespesasRepository repositoryDespesa;
	
	@Autowired
	private List<ValidadorCampoECadastroDaDescricaoMesmoMes> validador;

	public Despesas cadastrar(@Valid DadosCadastroEAtualizarDespesas dados) throws ValidacaoException {
		
		validador.forEach(v -> v.validar(dados));
		
		var cadastrarDespesa = new Despesas(dados);
		
		verificarCategoria(cadastrarDespesa);
		
		repositoryDespesa.save(cadastrarDespesa);
		
		return cadastrarDespesa;
		
		
	}

	public Despesas atualizarDespesas(@Valid DadosCadastroEAtualizarDespesas dados, Long id) throws ValidacaoException {
		
		if (!repositoryDespesa.existsById(id)) {
			throw new ValidacaoException("Despesa não encontrada");
		}
		
		validador.forEach(v -> v.validar(dados));
		
		var despesa = repositoryDespesa.getReferenceById(id);
		
		despesa.setDescricao(dados.descricao());
		despesa.setValor(dados.valor());
		despesa.setData(dados.data());

		return despesa;
	}
	
	public void verificarCategoria(Despesas despesa) {
		
		if (despesa.getCategoria() == null) {
			despesa.setCategoria(CategoriaDespesas.Outras);
		}
		
	}

}
