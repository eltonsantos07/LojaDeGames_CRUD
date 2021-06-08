package br.org.generation.lojaDeGames.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.generation.lojaDeGames.model.UserLogin;
import br.org.generation.lojaDeGames.model.Usuario;
import br.org.generation.lojaDeGames.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario CadastrarUsuaruio(Usuario usuario) {
		
		//proibir cadastro de usuarios iguais
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return null;
		
		//proibir mais de um cadastro por email
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			return null;
		
		//proibir cadatro de menores de 18 anos
		int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();
		
		if(idade < 18)
			return null;
		
		//proibir mais de um cadastro por CPF
		if(usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
			return null;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return usuarioRepository.save(usuario);
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(user.get().getUsuario());
		
		if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
			
			String auth = user.get().getUsuario() + ":" + user.get().getSenha();
			byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(encodeAuth);
			
			user.get().setToken(authHeader);
			user.get().setNome(usuario.get().getNome());
			user.get().setSenha(usuario.get().getSenha());
			
			return user;
		}
		
		return null;
	}
}
