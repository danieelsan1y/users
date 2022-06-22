package com.controle.usuarios.service.exceptions;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ServiceException(String email) {
		super("E-mail :" + email + " é Inválido!");
	}
}
