package com.controle.usuarios.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public @Data class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario",length = 9)
	private Long id;

	@NotEmpty(message = "O campo nome não pode ser vazio, insira novamente!")
	@Column(name = "nome_usuario",length = 20)
	private String nome;

	@NotEmpty(message = "O campo e-mail não pode ser vazio, insira novamente!")
	@Column(name = "email_usuario",length = 40)
	private String email;

	@NotEmpty(message = "O campo telefone não pode ser vazio, insira novamente!")
	@Column(name = "telefone_usuario",length = 20)
	private String telefone;

	@NotEmpty(message = "O rg nome não pode ser vazio, insira novamente!")
	@Column(name = "rg_usuario",length = 10)
	private String rg;

	@NotEmpty(message = "O cpf nome não pode ser vazio, insira novamente!")
	@Column(name = "cpf_usuario",length = 14)
	private String cpf;

	@NotNull(message = "A data não pode ser vazia, insira novamente!")
	@Column(name = "data_nascimento_usuario",length = 9)
	private Date dataNascimento;

	@NotEmpty(message = "O campo situação não pode ser vazio, insira novamente!")
	@Column(name = "situacao_usuario",length = 9)
	private String situacao;

}
