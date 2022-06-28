package com.controle.usuarios.service;

import com.controle.usuarios.dto.UsuarioInserirDTO;
import com.controle.usuarios.model.Usuario;
import com.controle.usuarios.service.exceptions.ServiceException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validate {

    public static void validarCpf(Usuario usuario) {
        if (usuario.getCpf().length() != 11) {
            throw new ServiceException("CPF inválido!");
        }
    }

    public static void validarSituacao(Usuario usuario) {
        if (!usuario.getSituacao().equals("Ativo") && !usuario.getSituacao().equals("Inativo")) {
            throw new ServiceException("Situacao inválida!");
        }
    }

    public static void validarTelefone(Usuario usuario) {
        if (usuario.getTelefone().length() != 11) {
            throw new ServiceException("Telefone inválido!");
        }
    }

    public static void validarData(UsuarioInserirDTO usuario) {
        if(usuario.getDataNascimento() !=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            String data = sdf.format(usuario.getDataNascimento());
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate d = LocalDate.parse(data, formatter);
            } catch (DateTimeParseException e) {
                throw new ServiceException("Data Inválida, insira novamente");
            }
        } else  {
            throw new ServiceException("A Data não pode ser vazia!");
        }

    }

    public static void validarEmail(Usuario usuario) {
        int validacao = 0;

        if (usuario.getEmail().length() > 2) {
            for (int i = 0; i < usuario.getEmail().length(); i++) {
                if (usuario.getEmail().charAt(i) == '@') {
                    validacao++;
                }
                if (usuario.getEmail().charAt(i) == '.') {
                    validacao++;
                }
            }
        }

        if (validacao != 2) {
            throw new ServiceException("E-mail inválido!");
        }
    }

    public static void validarSeCpfTemLetra(Usuario usuario) {
        boolean isNumeric =  usuario.getCpf().matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumeric == false) {
            throw new ServiceException("CPF contém letra!");
        }
    }

    public static void validarSeDataTemLetra(UsuarioInserirDTO usuario) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        String data = sdf.format(usuario.getDataNascimento());
        data = data.replaceAll("/", "");
        boolean isNumeric =  data.matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumeric == false) {
            throw new ServiceException("Data contém letra!");
        }
    }
    public static void validarSeRgTemLetras(Usuario usuario) {
        boolean isNumeric =  usuario.getRg().matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumeric == false) {
            throw new ServiceException("RG contém letra!");
        }
    }

    public static void validarSeTelefoneTemLetras(Usuario usuario) {
        boolean isNumeric =  usuario.getTelefone().matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumeric == false) {
            throw new ServiceException("Telefone contém letra!");
        }
    }
}
