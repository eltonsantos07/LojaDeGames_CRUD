package br.org.generation.lojaDeGames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.lojaDeGames.model.Categoria;
import br.org.generation.lojaDeGames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")
public class CategoriaController {

	@Autowired
	public CategoriaRepository categoriaRepository;

// =============== BUSCAR TODAS AS CATEGORIAS ===============

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {

		return ResponseEntity.ok(categoriaRepository.findAll());
	}

// =============== BUSCAR UMA CATEGORIA POR ID ===============

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> GetById(@PathVariable long id) {

		return categoriaRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

// =============== BUSCAR CATEGORIAS PELO NOME ===============

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Categoria>> GetByNome(@PathVariable String nome) {

		return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
// =============== INSERIR UMA NOVA CATEGORIA ===============

	@PostMapping
	public ResponseEntity<Categoria> PostCategoria (@RequestBody Categoria categoria) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}
	
// =============== ALTERAR UMA CATEGORIA ===============
	
	@PutMapping
	public ResponseEntity<Categoria> PutCategoria (@RequestBody Categoria categoria) {
		
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
	}
	
// ================ DELETAR UMA CATEGORIA ===============
	
	@DeleteMapping("/{id}")
	public void DeleteCategoria (@PathVariable Long id) {
		
		categoriaRepository.deleteById(id);
	}
}
