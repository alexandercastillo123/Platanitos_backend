package com.api.platanitos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.platanitos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findTop10ByEstadoOrderByFechaCreacionDesc(Producto.Estado estado);
}