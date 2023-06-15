package br.com.receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.receitas.domain.usuario.AutenticacaoService;
import br.com.receitas.domain.usuario.DadosAutenticacao;
import br.com.receitas.domain.usuario.DadosTrocarRole;
import br.com.receitas.domain.usuario.Usuario;
import br.com.receitas.domain.usuario.UsuarioRepository;
import br.com.receitas.infra.security.DadosTokenJWT;
import br.com.receitas.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/login")
	public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		service.validarLoginESenha(dados);
		var authenticationToke = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authenticationToke);
		//getPrincipal pega o usuario logado.  getPrincipal devolve um object por isso temos que fazer um cast pra usuario e o Spring conhece identificar pq implementamos a interface UserDetails na classe usuario 
		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		System.out.println(authentication.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}
	
	@PostMapping("/cadastro")
	@Transactional
	public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosAutenticacao dados) {
		var usuario = service.cadastrarUser(dados);
		return ResponseEntity.ok(200);
	}
	
	@PutMapping("/role")
	@Transactional
	public ResponseEntity trocarRoleUsuario(@RequestBody @Valid DadosTrocarRole dados) {
		service.setRoleUsuario(dados);
		return ResponseEntity.ok(200);

	}
	
	@DeleteMapping("/delete/{id}")
	@Transactional
	public ResponseEntity delete(@RequestBody @Valid @PathVariable Long id) {
		
		repository.deleteById(id);
		return ResponseEntity.ok(200);

	}

}
