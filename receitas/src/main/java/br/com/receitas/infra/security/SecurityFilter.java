package br.com.receitas.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.receitas.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component							//Essa classe que estamos herdando do spring OncePerRequestFilter garante que sera executada uma unica vez a cada requisição
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;

	//Sempre que chegar uma nova requisição API o spring ja sabe que tem essa classe/metodo
	@Override	//request pega coisas da requisição, response - enviar coisas na resposta, filterChain - representa a cadeia de filtros na aplicação
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//recupera o token da requisição
		var tokenJWT = recuperarToken(request);
		
		if (tokenJWT != null) {
			//getSubject pega o token do sujeito logado se nao estiver vazio/null, valida se o token está correto, pega o email usuario que está dentro do token
			var subject = tokenService.getSubject(tokenJWT);
			var usuario = repository.findByLogin(subject);
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario,  null, usuario.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}
	
	

}
