package br.com.receitas.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.receitas.domain.usuario.Usuario;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	@Query(value = "select id from perfil where id = :id", nativeQuery = true)
	Perfil encontrarLogin(Long id);
}
