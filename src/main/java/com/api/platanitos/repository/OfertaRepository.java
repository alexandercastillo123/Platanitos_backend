package com.api.platanitos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.platanitos.model.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
    List<Oferta> findByActivaTrueAndFechaFinGreaterThanEqual(LocalDate hoy);
}