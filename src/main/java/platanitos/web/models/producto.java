package platanitos.web.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private marca marca;

    @ManyToOne 
    @JoinColumn (name = "id_descuento")
    private  descuento descuento;

    @Column (name = "precio_base")
    private BigDecimal precioBase;

    private String descripcion;

    @Column (name = "url_imagen_principal")
    private String urlImagenPrincipal;

    private String estado;

    @Column (name = "fecha_creacion")
    private OffsetDateTime fecha_creacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public marca getMarca() {
        return marca;
    }

    public void setMarca(marca marca) {
        this.marca = marca;
    }

    public descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(descuento descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagenPrincipal() {
        return urlImagenPrincipal;
    }

    public void setUrlImagenPrincipal(String urlImagenPrincipal) {
        this.urlImagenPrincipal = urlImagenPrincipal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public OffsetDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(OffsetDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    
}
