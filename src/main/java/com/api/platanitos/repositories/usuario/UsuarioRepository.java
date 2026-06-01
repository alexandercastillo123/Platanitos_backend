package com.api.platanitos.repositories.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.platanitos.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmailOrTelefono(String email, String telefono);

    
}
