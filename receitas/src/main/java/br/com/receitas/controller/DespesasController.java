package br.com.receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.receitas.domain.despesas.DadosCadastroEAtualizarDespesas;
import br.com.receitas.domain.despesas.DadosDetalhamentoDespesas;
import br.com.receitas.domain.despesas.DespesasRepository;
import br.com.receitas.domain.despesas.ListarDespesas;
import br.com.receitas.domain.despesas.service.DespesasService;
import br.com.receitas.infra.exceptions.ValidacaoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("despesas")
public class DespesasController {
	
	@Autowired
	private DespesasService service;
	
	@Autowired
	private DespesasRepository repository;
	
	@Transactional
	@PostMapping
	public ResponseEntity cadastrarDespesas(@RequestBody @Valid DadosCadastroEAtualizarDespesas dados, UriComponentsBuilder uriBuilder) throws ValidacaoException {
		
		var cadastrar = service.cadastrar(dados);
		var uri = uriBuilder.path("/paciente/{}").buildAndExpand(cadastrar.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoDespesas(cadastrar));
	}
	
	@GetMapping
	public ResponseEntity listarDespesas(@RequestParam(value = "descricao", required = false) String descricao, @PageableDefault(sort = "descricao") Pageable page) {

		if (descricao != null) {
			var lista = repository.findByDescricaoContains(descricao, page).map(ListarDespesas::new);
			return ResponseEntity.ok(lista);
		}
		
		var lista = repository.findAll(page).map(ListarDespesas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity listarDespesasPeloMes(@PathVariable int ano, @PathVariable int mes, @PageableDefault(sort = "descricao") Pageable page) {

		var lista = repository.findAllByAnoAndMes(ano, mes, page).map(ListarDespesas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("{id}")
	public ResponseEntity listarDespesasPeloId(@PathVariable Long id) {

		var lista = repository.findById(id).map(ListarDespesas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity atualizarDespesasPeloId(@RequestBody @Valid DadosCadastroEAtualizarDespesas dados, @PathVariable Long id) throws ValidacaoException {

		service.atualizarDespesas(dados, id);
		
		
		var lista = repository.findById(id).map(ListarDespesas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity deletarDespesasPeloId(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
