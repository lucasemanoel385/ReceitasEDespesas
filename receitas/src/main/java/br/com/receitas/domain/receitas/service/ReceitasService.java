package br.com.receitas.domain.receitas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.domain.receitas.DadosDetalhamentoReceitas;
import br.com.receitas.domain.receitas.ListarReceitas;
import br.com.receitas.domain.receitas.Receitas;
import br.com.receitas.domain.receitas.ReceitasRepository;
import br.com.receitas.domain.validacoes.ValidadorCampoECadastroDaDescricaoMesmoMes;
import br.com.receitas.infra.exceptions.ValidacaoException;
import jakarta.validation.Valid;

@Service
public class ReceitasService {
	
	@Autowired
	private ReceitasRepository repository;
	
	@Autowired
	private List<ValidadorCampoECadastroDaDescricaoMesmoMes> validadores;
	
	public DadosDetalhamentoReceitas cadastrar(@Valid DadosCadastroEAtualizarReceita dados) throws ValidacaoException {
		validadores.forEach(v -> v.validar(dados));
		
		var cadastrar = new Receitas(dados);
		repository.save(cadastrar);
		
		return new DadosDetalhamentoReceitas(cadastrar);
	}
	
	public ListarReceitas atualizarReceita(@Valid DadosCadastroEAtualizarReceita dados, Long id) throws ValidacaoException {
		
		if (!repository.existsById(id)) {
			throw new ValidacaoException("Receita não encontrada");
		}
		
		validadores.forEach(v -> v.validar(dados));
		
		var receita = repository.getReferenceById(id);
		receita.setDescricao(dados.descricao());
		receita.setValor(dados.valor());
		receita.setData(dados.data());
		
		return new ListarReceitas(receita);
	}
	
	@GetMapping
	public Page<ListarReceitas> listarReceitas(@RequestParam(value = "descricao", required = false) String descricao, @PageableDefault(sort = "descricao") Pageable page) {

		if (descricao != null) {
			var lista = repository.findByDescricaoContains(descricao, page).map(ListarReceitas::new);
			return lista;
		}
		
		var lista = repository.findAll(page).map(ListarReceitas::new);
		
		return lista;
	}
	

}
