package com.api.platanitos.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.platanitos.model.Categoria;
import com.api.platanitos.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
    


@Service @RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;
    public List<Categoria> getCategoriasRaiz() {
        return repository.findByActivaTrueAndNivel(1);
    }
}