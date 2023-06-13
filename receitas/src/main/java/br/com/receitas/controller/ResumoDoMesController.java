package br.com.receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.receitas.domain.resumo.service.ResumoService;

@RestController
@RequestMapping("resumo")
public class ResumoDoMesController {
	
	@Autowired
	private ResumoService service;
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity resumoMes(@PathVariable int ano, @PathVariable int mes, Pageable page) {
		var resumo = service.resumoDoMes(ano, mes, page);
		
		return ResponseEntity.ok(resumo);
	}

}
