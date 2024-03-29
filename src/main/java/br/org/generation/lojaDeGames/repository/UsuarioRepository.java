package br.org.generation.lojaDeGames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.generation.lojaDeGames.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByUsuario(String usuario);

	public Optional<Usuario> findByEmail(String email);
	
	public Optional<Usuario> findByCpf(String cpf);

}
