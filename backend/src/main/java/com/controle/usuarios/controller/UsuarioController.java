package com.controle.usuarios.controller;

import java.net.URI;
import java.util.List;

import com.controle.usuarios.dto.UsuarioDTO;
import com.controle.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;

@Controller
@RequestMapping(value = "/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

	@Autowired
	UsuarioService service;

	@PostMapping
	ResponseEntity<Void> salvar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		UsuarioDTO user = service.salvar(usuarioDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping (value = "{id}")
	ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		UsuarioDTO usuarioDTO = service.buscarPorId(id);
		return ResponseEntity.ok().body(usuarioDTO);
	}
	@PutMapping(value = "/{id}")
	ResponseEntity<Void> alterarUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
		service.atualizarPorId(usuarioDTO, id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "situacao/{cpf}")
	ResponseEntity<Void> alterarSituacao(@PathVariable String cpf, @RequestBody UsuarioDTO dtousuario) {
		dtousuario.setCpf(cpf);
		service.alterarSituacao(dtousuario);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "status/{status}")
	ResponseEntity<List<UsuarioDTO>> buscarStatus(@PathVariable String status) {
		List<UsuarioDTO> usuariosDTO = service.buscarStatus(status);
		return ResponseEntity.ok().body(usuariosDTO);
	}

	@GetMapping(value = "nome/{nome}")
	ResponseEntity<List<UsuarioDTO>> buscarPorNome(@PathVariable String nome) {
		List<UsuarioDTO> usuariosDTO = service.buscarPorNome(nome);
		return ResponseEntity.ok().body(usuariosDTO);
	}

	@GetMapping(value = "idade/{idade}")
	ResponseEntity<List<UsuarioDTO>> buscarPorIdade(@PathVariable Integer idade) {
		System.out.println(idade);
		List<UsuarioDTO> usuariosDTO = service.buscarPorIdade(idade);

		return ResponseEntity.ok().body(usuariosDTO);
	}
/*
	@DeleteMapping(value = "/{cpf}")
	ResponseEntity<Void> deletarUsuario(@PathVariable String cpf) {
		service.deletar(cpf);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{cpf}")
	ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable String cpf) {
		service.atualizar(usuarioDTO, cpf);
		return ResponseEntity.noContent().build();
	}

 */
}
