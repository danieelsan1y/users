package com.controle.usuarios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.controle.usuarios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.cpf = :cpf")
	public Usuario encontrarPorCpf(@Param("cpf") String cpf);

	@Query("select u from Usuario u where u.situacao = :situacao ORDER BY u.id ASC")
	public List<Usuario> buscarStatusUsuario(@Param("situacao") String situacao);

	public List<Usuario> findByNome(String nome);

	@Query("select u from Usuario u where u.id = :id")
	public Usuario buscarPorId(Long id);

}
