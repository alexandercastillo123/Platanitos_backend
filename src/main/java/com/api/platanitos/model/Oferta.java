package com.api.platanitos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "oferta")
@Data 
@NoArgsConstructor @AllArgsConstructor
public class Oferta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descripcion;
    private String urlImagen;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;
}