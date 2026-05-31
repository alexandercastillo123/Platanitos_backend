package com.api.platanitos.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria")
@Data @NoArgsConstructor @AllArgsConstructor
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Categoria parent;
    private Integer nivel;
    private String icono;
    private Boolean activa;
}