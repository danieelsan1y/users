package com.controle.usuarios.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.controle.usuarios.dto.UsuarioInserirDTO;
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


    public UsuarioInserirDTO salvar(@Valid UsuarioInserirDTO usuarioInserirDTO) {
        validarUsuarioDTO(usuarioInserirDTO);
        Usuario novoUsuario = converterParaUsuario(usuarioInserirDTO);
        retirarMascaraCpf(novoUsuario);
        retirarMascaraTelefone(novoUsuario);
        validarUsuario(novoUsuario);
        Usuario usuario = repository.encontrarPorCpf(novoUsuario.getCpf());
        if (verificarSeUsuarioExisteNoBanco(usuario)) {
            if (usuario.getSituacao().equals("Inativo")) {
                inserirNosCampos(usuario, usuarioInserirDTO);
                return new UsuarioInserirDTO(usuario);
            } else {
                throw new ServiceException("Usuário já cadastrado no banco!");
            }
        } else {
            repository.save(novoUsuario);
            return new UsuarioInserirDTO(novoUsuario);
        }
    }

    public void inserirNosCampos(Usuario usuario, UsuarioInserirDTO usuarioInserirDTO){
        retirarMascaraCpf(usuario);
        retirarMascaraTelefone(usuario);
        usuario.setSituacao("Ativo");
        usuario.setEmail(usuarioInserirDTO.getEmail());
        usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());
        usuario.setTelefone(usuario.getTelefone());
        usuario.setRg(usuarioInserirDTO.getRg());
        usuario.setNome(usuarioInserirDTO.getNome());
        repository.save(usuario);
    }
    public void deletar(String cpf) {
        Usuario user = repository.encontrarPorCpf(cpf);
        repository.deleteById(user.getId());
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = repository.buscarPorId(id);
        colocarMascaraCpf(usuario);
        colocarMascaraTelefone(usuario);
        UsuarioDTO usuarioDateDTO = new UsuarioDTO(usuario);
        return usuarioDateDTO;
    }


    public UsuarioDTO atualizarPorId(UsuarioDTO usuario, Long id) {
        Usuario usuarioAntigo = repository.buscarPorId(id);
        Usuario usuarioNovo = converterUsuarioDTOParaUsuario(usuario);
        atualizarCampos(usuarioNovo, usuarioAntigo);
        retirarMascaraCpf(usuarioAntigo);
        retirarMascaraTelefone(usuarioAntigo);
        validarUsuario(usuarioAntigo);
        repository.save(usuarioAntigo);


        return new UsuarioDTO(usuarioAntigo);
    }

    public void alterarSituacao(Long id, String situacao) {
        Usuario novoUsuario = repository.buscarPorId(id);
        novoUsuario.setSituacao(situacao);
        repository.save(novoUsuario);
    }

    public List<UsuarioDTO> buscarSituacao(String situacao) {
        List<Usuario> usuarios = repository.buscarStatusUsuario(situacao);
        for (Usuario user : usuarios) {
            colocarMascaraTelefone(user);
            colocarMascaraCpf(user);
        }

        List<UsuarioDTO> usuariosDateDTO = usuarios.stream().map(x -> new UsuarioDTO(x)).collect(Collectors.toList());


        return usuariosDateDTO;
    }

    public List<UsuarioInserirDTO> buscarPorNome(String nome) {
        List<Usuario> usuarios = repository.findByNome(nome);
        List<UsuarioInserirDTO> usuariosDTO = usuarios.stream().map(x -> new UsuarioInserirDTO(x)).collect(Collectors.toList());
        return usuariosDTO;

    }

    public Usuario converterParaUsuario(UsuarioInserirDTO objDTO) {
        Usuario user = new Usuario(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), objDTO.getTelefone(),
                objDTO.getRg(), objDTO.getCpf(), objDTO.getDataNascimento(), objDTO.getSituacao());
        return user;
    }
    public Usuario converterUsuarioDTOParaUsuario(UsuarioDTO objDTO) {
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

    public List<UsuarioInserirDTO> buscarPorIdade(Integer idade) {
        List<UsuarioInserirDTO> todosUsuariosDTO = repository.findAll().stream().map(x -> new UsuarioInserirDTO(x))
                .collect(Collectors.toList());
        List<UsuarioInserirDTO> usuariosComIdade = new ArrayList<>();

        for (UsuarioInserirDTO user : todosUsuariosDTO) {
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

    private void validarUsuarioDTO(UsuarioInserirDTO usuarioInserirDTO) {
        Validate.validarData(usuarioInserirDTO);
        Validate.validarSeDataTemLetra(usuarioInserirDTO);
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
