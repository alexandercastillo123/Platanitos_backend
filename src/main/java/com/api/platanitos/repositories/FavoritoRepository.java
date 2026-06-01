package com.api.platanitos.repositories;

import com.api.platanitos.models.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    
    List<Favorito> findByUsuario_Id(Long usuarioId);
    Optional<Favorito> findByUsuario_IdAndProducto_Id(Long usuarioId, Integer productoId);
}