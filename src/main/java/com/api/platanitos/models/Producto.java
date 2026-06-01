package com.api.platanitos.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "producto", schema = "public")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String slug;

    @Column(name = "precio_base")
    private BigDecimal precioBase;

    private String descripcion;

    @Column(name = "url_imagen_principal")
    private String urlImagenPrincipal;

    private String estado;
}
