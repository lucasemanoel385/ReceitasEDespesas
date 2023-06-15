package br.com.receitas.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	UserDetails findByLogin(String login);
	
	@Query(value = "select * from usuarios where login = :login", nativeQuery = true)
	Usuario encontrarLogin(String login);

}
