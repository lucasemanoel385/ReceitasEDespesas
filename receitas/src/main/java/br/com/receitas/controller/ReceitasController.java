package br.com.receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.domain.receitas.ListarReceitas;
import br.com.receitas.domain.receitas.ReceitasRepository;
import br.com.receitas.domain.receitas.service.ReceitasService;
import br.com.receitas.infra.exceptions.ValidacaoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("receitas")
public class ReceitasController {
	
	@Autowired
	private ReceitasRepository repository;
	
	@Autowired
	private ReceitasService service;

	
	@Transactional
	@PostMapping
	public ResponseEntity cadastrarReceitas(@RequestBody @Valid DadosCadastroEAtualizarReceita dados, UriComponentsBuilder uriBuilder) throws ValidacaoException {
		log.info("Cadastrando receitas");
		var cadastrar = service.cadastrar(dados);
		var uri = uriBuilder.path("/paciente/{}").buildAndExpand(cadastrar.id()).toUri();
		
		return ResponseEntity.created(uri).body(cadastrar);
	}
	
	
	@GetMapping
	public ResponseEntity<Page<ListarReceitas>> listarReceitas(@RequestParam(value = "descricao", required = false) String descricao, @PageableDefault(sort = "descricao") Pageable page) {
		log.info("Listado receitas");
		var lista = service.listarReceitas(descricao, page);
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<Page<ListarReceitas>> listarReceitasPeloMes(@PathVariable int ano, @PathVariable int mes, @PageableDefault(sort = "descricao") Pageable page) {
		log.info("Listado receita pelo mês e o ano");
		var lista = repository.findAllByAnoAndMes(ano, mes, page).map(ListarReceitas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("{id}")
	public ResponseEntity listarReceitasPeloId(@PathVariable Long id) {
		log.info("Listado receita pelo id: ", id);
		var lista = repository.findById(id).map(ListarReceitas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity atualizarReceitasPeloId(@RequestBody @Valid DadosCadastroEAtualizarReceita dados, @PathVariable Long id) throws ValidacaoException {
		log.info("Atualizar receita pelo id: " + id);
		service.atualizarReceita(dados, id);
		
		
		var lista = repository.findById(id).map(ListarReceitas::new);
		
		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity deletarReceitasPeloId(@PathVariable Long id) {
		log.info("Deletar receita pelo ID: " + id);
		if (!repository.existsById(id)) {
			throw new ValidacaoException("Receita não encontrado");
		}

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
