package com.controle.usuarios.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controle.usuarios.dto.UsuarioDTO;
import com.controle.usuarios.model.Usuario;
import com.controle.usuarios.repository.UsuarioRepository;
import com.controle.usuarios.service.exceptions.ServiceException;

@Service
public class UsuarioService {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	@Autowired
	private UsuarioRepository repository;

	public UsuarioDTO salvar(UsuarioDTO userDTO) {
		Usuario user = converterParaUsuario(userDTO);
		if (validarEmail(user)) {
			retirarMascaraEmail(user);
			retirarMascaraTelefone(user);
			repository.save(user);
		} else {
			throw new ServiceException(user.getEmail());
		}
		return new UsuarioDTO(user);
	}

	public void deletar(String cpf) {
		Usuario user = repository.encontrarPorCpf(cpf);
		repository.deleteById(user.getId());
	}

	public UsuarioDTO atualizar(UsuarioDTO user, String cpf) {
		Usuario usuarioAntigo = repository.encontrarPorCpf(cpf);
		Usuario usuarioNovo = converterParaUsuario(user);
		atualizarCampos(usuarioNovo, usuarioAntigo);
		System.out.println(usuarioAntigo);

		if (validarEmail(usuarioAntigo)) {
			retirarMascaraEmail(usuarioAntigo);
			retirarMascaraTelefone(usuarioAntigo);
			repository.save(usuarioAntigo);
		}

		return new UsuarioDTO(usuarioAntigo);
	}

	public Usuario alterarSituacao(UsuarioDTO userDTO) {
		Usuario user = converterParaUsuario(userDTO);
		Usuario newUser = repository.encontrarPorCpf(user.getCpf());

		System.out.println(newUser);
		newUser.setSituacao(user.getSituacao());
		repository.save(newUser);

		return user;
	}

	public List<UsuarioDTO> buscarStatus(String status) {
		List<Usuario> usuarios = repository.buscarStatusUsuario(status);
		for (Usuario user : usuarios) {
			colocarMascaraTelefone(user);
			colocarMascaraEmail(user);
		}

		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(x -> new UsuarioDTO(x)).collect(Collectors.toList());
		
		
		return usuariosDTO;
	}

	public List<UsuarioDTO> buscarPorNome(String nome) {
		List<Usuario> usuarios = repository.findByNome(nome);
		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(x -> new UsuarioDTO(x)).collect(Collectors.toList());
		return usuariosDTO;

	}

	public Usuario converterParaUsuario(UsuarioDTO objDTO) {
		Usuario user = new Usuario(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), objDTO.getTelefone(),
				objDTO.getRg(), objDTO.getCpf(), objDTO.getDataNascimento(), objDTO.getSituacao());
		return user;
	}

	private boolean validarEmail(Usuario user) {
		int validacao = 0;

		if (user.getEmail().length() > 2) {
			for (int i = 0; i < user.getEmail().length(); i++) {
				if (user.getEmail().charAt(i) == '@') {
					validacao++;
				}
				if (user.getEmail().charAt(i) == '.') {
					validacao++;
				}
			}
		}

		if (validacao == 2) {
			return true;
		} else {
			return false;
		}
	}

	private void retirarMascaraEmail(Usuario user) {

		String cpfnovo = user.getCpf();
		cpfnovo = cpfnovo.replaceAll("\\.", "");
		cpfnovo = cpfnovo.replaceAll("-", "");
		user.setCpf(cpfnovo);

	}

	private void colocarMascaraEmail(Usuario user) {

		StringBuilder cpfnovo = new StringBuilder(user.getCpf());
		cpfnovo = cpfnovo.insert(3, ".");
		cpfnovo = cpfnovo.insert(7, ".");
		cpfnovo = cpfnovo.insert(11, "-");

		user.setCpf(cpfnovo.toString());

	}

	private void retirarMascaraTelefone(Usuario user) {

		StringBuilder telNovo = new StringBuilder(user.getTelefone());
		telNovo = telNovo.deleteCharAt(0);
		telNovo = telNovo.deleteCharAt(2);
		telNovo = telNovo.deleteCharAt(7);
		user.setTelefone(telNovo.toString());

	}

	private void colocarMascaraTelefone(Usuario user) {

		StringBuilder telNovo = new StringBuilder(user.getTelefone());
		telNovo = telNovo.insert(0, "(");
		telNovo = telNovo.insert(3, ")");
		telNovo = telNovo.insert(9, "-");
		user.setTelefone(telNovo.toString());
	}

	public List<UsuarioDTO> buscarPorIdade(Integer idade) {
		List<UsuarioDTO> todosUsuariosDTO = repository.findAll().stream().map(x -> new UsuarioDTO(x))
				.collect(Collectors.toList());
		List<UsuarioDTO> usuariosComIdade = new ArrayList<>();

		for (UsuarioDTO user : todosUsuariosDTO) {
			if (user.getIdade() == idade) {
				usuariosComIdade.add(user);
			}
		}
		return usuariosComIdade;
	}

	private void atualizarCampos(Usuario novo, Usuario antigo) {
		antigo.setCpf(novo.getCpf());
		antigo.setDataNascimento(novo.getDataNascimento());
		antigo.setEmail(novo.getEmail());
		antigo.setNome(novo.getNome());
		antigo.setRg(novo.getRg());
		antigo.setSituacao(novo.getSituacao());
		antigo.setTelefone(novo.getTelefone());
	}

}
