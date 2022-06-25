package com.controle.usuarios.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.controle.usuarios.dto.UsuarioDTO;
import com.controle.usuarios.repository.UsuarioRepository;
import com.controle.usuarios.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controle.usuarios.model.Usuario;

import javax.validation.Valid;

@Service
public class UsuarioService {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    @Autowired
    private UsuarioRepository repository;


    public UsuarioDTO salvar(@Valid UsuarioDTO usuarioDTO) {
        validarUsuarioDTO(usuarioDTO);
        Usuario novoUsuario = converterParaUsuario(usuarioDTO);
        retirarMascaraCpf(novoUsuario);
        retirarMascaraTelefone(novoUsuario);
        validarUsuario(novoUsuario);
        Usuario usuario = repository.encontrarPorCpf(novoUsuario.getCpf());
        if (verificarSeUsuarioExisteNoBanco(usuario)) {
            if (usuario.getSituacao().equals("Inativo")) {
                inserirNosCampos(usuario,usuarioDTO);
                return new UsuarioDTO(usuario);
            } else {
                throw new ServiceException("Usuário já cadastrado no banco!");
            }
        } else {
            repository.save(novoUsuario);
            return new UsuarioDTO(novoUsuario);
        }
    }

    public void inserirNosCampos(Usuario usuario, UsuarioDTO usuarioDTO){
        retirarMascaraCpf(usuario);
        retirarMascaraTelefone(usuario);
        usuario.setSituacao("Ativo");
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setTelefone(usuario.getTelefone());
        usuario.setRg(usuarioDTO.getRg());
        usuario.setNome(usuarioDTO.getNome());
        repository.save(usuario);
    }
    public void deletar(String cpf) {
        Usuario user = repository.encontrarPorCpf(cpf);
        repository.deleteById(user.getId());
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = repository.buscarPorId(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        return usuarioDTO;
    }


    public UsuarioDTO atualizarPorId(UsuarioDTO user, Long id) {
        Usuario usuarioAntigo = repository.buscarPorId(id);
        Usuario usuarioNovo = converterParaUsuario(user);
        atualizarCampos(usuarioNovo, usuarioAntigo);
        retirarMascaraCpf(usuarioAntigo);
        retirarMascaraTelefone(usuarioAntigo);
        validarUsuario(usuarioAntigo);
        repository.save(usuarioAntigo);


        return new UsuarioDTO(usuarioAntigo);
    }

    public Usuario alterarSituacao(Long id, UsuarioDTO userDTO) {
        Usuario novoUsuario = converterParaUsuario(userDTO);
        Usuario newUser = repository.buscarPorId(id);
        newUser.setSituacao(novoUsuario.getSituacao());
        repository.save(novoUsuario);

        return novoUsuario;
    }

    public List<UsuarioDTO> buscarStatus(String status) {
        List<Usuario> usuarios = repository.buscarStatusUsuario(status);
        for (Usuario user : usuarios) {
            colocarMascaraTelefone(user);
            colocarMascaraCpf(user);
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


    private void retirarMascaraCpf(Usuario user) {

        String cpfnovo = user.getCpf();
        cpfnovo = cpfnovo.replaceAll("\\.", "");
        cpfnovo = cpfnovo.replaceAll("-", "");
        user.setCpf(cpfnovo);

    }

    private void colocarMascaraCpf(Usuario user) {

        StringBuilder cpfnovo = new StringBuilder(user.getCpf());
        cpfnovo = cpfnovo.insert(3, ".");
        cpfnovo = cpfnovo.insert(7, ".");
        cpfnovo = cpfnovo.insert(11, "-");

        user.setCpf(cpfnovo.toString());

    }

    private void retirarMascaraTelefone(Usuario user) {

        String telNovo = user.getTelefone();

        telNovo = telNovo.replaceAll("\\(", "");
        telNovo = telNovo.replaceAll("\\)", "");
        telNovo = telNovo.replaceAll("-", "");
        user.setTelefone(telNovo);

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

    private boolean verificarSeUsuarioExisteNoBanco(Usuario usuario) {
        if (usuario != null) {
            return true;
        }
        return false;
    }

    private void validarUsuarioDTO(UsuarioDTO usuarioDTO) {
        Validate.validarData(usuarioDTO);
        Validate.validarSeDataTemLetra(usuarioDTO);
    }
    private void validarUsuario(Usuario usuario) {
        Validate.validarTelefone(usuario);
        Validate.validarSituacao(usuario);
        Validate.validarCpf(usuario);
        Validate.validarEmail(usuario);

        Validate.validarSeTelefoneTemLetras(usuario);
        Validate.validarSeCpfTemLetra(usuario);
        Validate.validarSeRgTemLetras(usuario);
    }
}
