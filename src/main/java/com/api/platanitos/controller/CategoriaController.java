package com.api.platanitos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.api.platanitos.model.Categoria;
import com.api.platanitos.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService service;

    @GetMapping
    public List<Categoria> getCategorias() { return service.getCategoriasRaiz(); }
}