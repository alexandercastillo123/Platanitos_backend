package com.api.platanitos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.platanitos.model.Oferta;
import com.api.platanitos.service.OfertaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
public class OfertaController {
    private final OfertaService service;

    @GetMapping
    public List<Oferta> getOfertas() { return service.getOfertasActivas(); }
}