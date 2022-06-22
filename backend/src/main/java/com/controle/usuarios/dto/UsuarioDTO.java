package com.controle.usuarios.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import com.controle.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String rg;
	private String cpf;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy")
	private Date dataNascimento;
	private String situacao;
	private Integer idade;

	public UsuarioDTO(Usuario user) {
		this.cpf = user.getCpf();
		this.id = user.getId();
		this.nome = user.getNome();
		this.email = user.getEmail();
		this.telefone = user.getTelefone();
		this.rg = user.getRg();
		this.dataNascimento = user.getDataNascimento();
		this.situacao = user.getSituacao();
		calcularIdade();
	}

	public void calcularIdade() {
		if (!(dataNascimento.equals(null))) {
			LocalDate nascimento = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			final LocalDate dataAtual = LocalDate.now();
			final Period periodo = Period.between(nascimento, dataAtual);
			idade = periodo.getYears();
		}

	}

}
