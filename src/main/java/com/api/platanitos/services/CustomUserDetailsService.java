package com.api.platanitos.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.platanitos.models.Usuario;
import com.api.platanitos.repositories.usuario.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailOrTelefono(identificador, identificador)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String usernameOficial = (usuario.getEmail() != null) ? usuario.getEmail() : usuario.getTelefono(); 

        return User.withUsername(usernameOficial)
                .password(usuario.getPasswordHash())
                .disabled(!usuario.getEstado())
                .accountLocked(!usuario.getVerificado())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name())))
                .build();
    }
}
