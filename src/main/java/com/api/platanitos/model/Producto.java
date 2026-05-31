package com.api.platanitos.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@Data @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
    private BigDecimal precioBase;
    private String urlImagenPrincipal;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private LocalDateTime fechaCreacion;

    public enum Estado { activo, inactivo, agotado }
}