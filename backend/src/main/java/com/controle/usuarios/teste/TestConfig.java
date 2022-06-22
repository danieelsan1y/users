package com.controle.usuarios.teste;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.controle.usuarios.model.Usuario;
import com.controle.usuarios.repository.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) throws Exception {

		Usuario user1 = new Usuario(null, "Daniel", "daniel@gmail.com", "6299317-5820", "6283063", "70342955144",
				sdf.parse("07/03/1999"), "Ativo");
		Usuario user2 = new Usuario(null, "Daniel", "danillo@gmail.com", "62992417980", "6700124", "81349700100",
				sdf.parse("01/03/1987"), "Ativo");
		Usuario user3 = new Usuario(null, "Efigenia", "efigenia@gmail.com", "62993112345", "9023467", "71232509144",
				sdf.parse("15/04/1959"), "Inativo");
		Usuario user4 = new Usuario(null, "Danielle", "danielle@gmail.com", "62993467234", "6098652", "10829637891",
				sdf.parse("01/03/1984"), "Inativo");
		usuarioRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

	}

}
