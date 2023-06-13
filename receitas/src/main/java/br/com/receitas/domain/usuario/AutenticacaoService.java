package br.com.receitas.domain.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.receitas.infra.exceptions.AutenticacaoException;
import jakarta.validation.Valid;


@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder test;
	
	private final Logger logger = LoggerFactory.getLogger(AutenticacaoService.class);
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		var t = repository.findByLogin(login);
		return t;
	}

	public UserDetails validarLoginESenha(@Valid DadosAutenticacao dados) {
		
		var login = repository.findByLogin(dados.login());
		
		if(login == null) {
			throw new AutenticacaoException("Login invalido");
		}
		
		var p = test.matches(dados.senha(), login.getPassword());
		
		if(!p) {
			throw new AutenticacaoException("Senha invalida");
		}
		return login;
	}
	

	private String getLogado() {
								// Verificar qual usuario está logado no sistema
		Authentication usuarioLogado = SecurityContextHolder.getContext().getAuthentication();
		// Verificar se o usuario está logado
		if (!(usuarioLogado instanceof AnonymousAuthenticationToken)) {
			return usuarioLogado.getName();
		}
		return "null";
	}
		
}

