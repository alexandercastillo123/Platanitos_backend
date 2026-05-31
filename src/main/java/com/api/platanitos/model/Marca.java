package com.api.platanitos.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca")
@Data @NoArgsConstructor @AllArgsConstructor
public class Marca {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String logoUrl;
}