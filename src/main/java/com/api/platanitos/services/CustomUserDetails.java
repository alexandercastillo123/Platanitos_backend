package com.api.platanitos.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.platanitos.models.Usuario;
import com.api.platanitos.repositories.usuario.UsuarioRepository;


public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .disabled(!usuario.getActivo())
                .accountLocked(!usuario.getActivo())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name())))
                .build();
    }
    
}
