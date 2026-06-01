package com.api.platanitos.util.auth;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidacionFormato {
    private static final Pattern REGEX_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern REGEX_TELEFONO = Pattern.compile("^(\\+?[0-9]){9,15}$");

    public void validarCampos(String tipo, String identificador){
        if(tipo == null) throw new RuntimeException("Tipo de autenticación vacio ");

        if("email".equals(tipo)){
            if(!REGEX_EMAIL.matcher(identificador).matches())
                throw new RuntimeException("Formato de email inválido");
        }
        else if("tel".equals(tipo)){
            if(!REGEX_TELEFONO.matcher(identificador).matches())
                throw new RuntimeException("Formato de teléfono inválido");
        }
        else throw new RuntimeException("Tipo de autenticación inválido: " + tipo);
    }
    public void validarCamposInverso(String tipo, String identificador){
        if(tipo == null) throw new RuntimeException("Tipo de autenticación vacio ");

        if("email".equals(tipo)){
            if(!REGEX_TELEFONO.matcher(identificador).matches())
                throw new RuntimeException("Formato de teléfono inválido");
        }
        else if("tel".equals(tipo)){
            if(!REGEX_EMAIL.matcher(identificador).matches())
                throw new RuntimeException("Formato de email inválido");
        }
        else throw new RuntimeException("Tipo de autenticación inválido: " + tipo);
    }
}
