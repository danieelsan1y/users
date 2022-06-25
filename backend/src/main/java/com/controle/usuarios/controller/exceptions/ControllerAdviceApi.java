package com.controle.usuarios.controller.exceptions;


import com.controle.usuarios.exceptions.ServiceErro;
import com.controle.usuarios.service.exceptions.DatabaseException;
import com.controle.usuarios.service.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerAdviceApi {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceErro> resourceNotFound(ServiceException e, HttpServletRequest request) {
        String erro = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ServiceErro err = new ServiceErro(String.valueOf(status), e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ServiceErro> resourceNotFound(ConstraintViolationException e) {
        String erro = "Campo(s) obrigatório(s) em branco";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ServiceErro err = new ServiceErro(String.valueOf(status), "Campos Obrigatórios em branco");
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ServiceErro> database(DatabaseException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status =  HttpStatus.BAD_REQUEST;
        ServiceErro err = new ServiceErro(String.valueOf(status), "Erro na Database");
        return ResponseEntity.status(status).body(err);
    }
}