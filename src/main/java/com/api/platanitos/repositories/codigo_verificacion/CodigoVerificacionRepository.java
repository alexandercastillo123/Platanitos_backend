package com.api.platanitos.repositories.codigo_verificacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.platanitos.models.CodigoVerificacion;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long>{
    @Query("SELECT c FROM CodigoVerificacion c JOIN FETCH c.usuario u WHERE c.token = :token AND u.id = :idUsuario")
    Optional<CodigoVerificacion> findByTokenAndUsuarioIdWithUsuario(
        @Param("token") String token, 
        @Param("idUsuario") Long idUsuario
    );
}
