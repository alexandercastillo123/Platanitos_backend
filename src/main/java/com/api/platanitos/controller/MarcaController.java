package com.api.platanitos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.api.platanitos.model.Marca;
import com.api.platanitos.service.MarcaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final MarcaService service;

    @GetMapping
    public List<Marca> getMarcas() { return service.getAll(); }
}