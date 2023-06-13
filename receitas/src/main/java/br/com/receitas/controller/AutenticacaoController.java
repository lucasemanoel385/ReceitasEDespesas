package br.com.receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.receitas.domain.usuario.AutenticacaoService;
import br.com.receitas.domain.usuario.DadosAutenticacao;
import br.com.receitas.domain.usuario.Usuario;
import br.com.receitas.infra.security.DadosTokenJWT;
import br.com.receitas.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService service;
	
	@PostMapping
	public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		
		service.validarLoginESenha(dados);
		
		var authenticationToke = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authenticationToke);
		//getPrincipal pega o usuario logado.  getPrincipal devolve um object por isso temos que fazer um cast pra usuario e o Spring conhece identificar pq implementamos a interface UserDetails na classe usuario 
		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

}
