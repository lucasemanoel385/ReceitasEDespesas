package br.com.receitas.domain.usuario;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.receitas.domain.perfil.Perfil;
import br.com.receitas.domain.perfil.PerfilRepository;
import br.com.receitas.infra.exceptions.AutenticacaoException;
import br.com.receitas.infra.exceptions.ChaveDuplicadaBDException;
import jakarta.validation.Valid;


					//O Spring secutiry tem que saber qual classe que faz o serviço de autenticação
					//Usamos a interface UserDetailsService pra fazer essa identificação, na hora da autenticação o spring  encontra ela sozinha graças há isso
@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder test;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	private final Logger logger = LoggerFactory.getLogger(AutenticacaoService.class);
	
	@Override 	//Quando o usuario fizer login é esse metodo que será chamado pra verificar caso nao encontre manda um UsernameNotFound(Não encontrado)
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
	

//	private String getLogado() {
//								// Verificar qual usuario está logado no sistema
//		Authentication usuarioLogado = SecurityContextHolder.getContext().getAuthentication();
//		// Verificar se o usuario está logado
//		if (!(usuarioLogado instanceof AnonymousAuthenticationToken)) {
//			return usuarioLogado.getName();
//		}
//		return "null";
//	}

	public Usuario cadastrarUser(@Valid DadosAutenticacao dados) {
		if (!(repository.encontrarLogin(dados.login()) == null)) {
			throw new ChaveDuplicadaBDException("Esse login já existe");
	}
		var senha = test.encode(dados.senha());
		var usuario = new Usuario(dados.login(), senha);
		repository.save(usuario);
		rolePadraoUser(usuario);
		return usuario;
	}
	
	public void setRoleUsuario(@Valid DadosTrocarRole dados) {
		if (repository.encontrarLogin(dados.login()) == null) {
				throw new AutenticacaoException("Login não encontrado");
		}
		var usuario = repository.encontrarLogin(dados.login());
		var perfilRole = perfilRepository.getReferenceById(dados.id());
		List<Perfil> perfil = new ArrayList<Perfil>();
		perfil.add(perfilRole);
		usuario.setPerfis(perfil);
		
	}
	
	private void rolePadraoUser(Usuario usuario) {
		
		if (usuario.getPerfis().isEmpty()) {
			List<Perfil> perfil = new ArrayList<Perfil>();
			var repo = perfilRepository.getReferenceById(1l);
			perfil.add(repo);
			usuario.setPerfis(perfil);
			
		}
	}
		
}

