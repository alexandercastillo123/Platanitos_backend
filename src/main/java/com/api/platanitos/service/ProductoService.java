package com.api.platanitos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.platanitos.model.Producto;
import com.api.platanitos.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;
    public List<Producto> getProductosNuevos() {
        return repository.findTop10ByEstadoOrderByFechaCreacionDesc(Producto.Estado.activo);
    }
}