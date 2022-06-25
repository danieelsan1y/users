package com.controle.usuarios.service.exceptions;


public class ServiceException extends RuntimeException{

    public ServiceException (String mensagem) {
        super(mensagem);
    }
}