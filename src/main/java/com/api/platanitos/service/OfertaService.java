package com.api.platanitos.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.api.platanitos.model.Oferta;
import com.api.platanitos.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class OfertaService {
    private final OfertaRepository repository;
    public List<Oferta> getOfertasActivas() {
        return repository.findByActivaTrueAndFechaFinGreaterThanEqual(LocalDate.now());
    }
}