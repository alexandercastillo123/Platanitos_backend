package com.api.platanitos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.api.platanitos.model.Marca;
import com.api.platanitos.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class MarcaService {
    private final MarcaRepository repository;
    public List<Marca> getAll() { return repository.findAll(); }
}