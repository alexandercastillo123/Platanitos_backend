package platanitos.web.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_item")
public class pedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_variante_id")
    private productoVariante productoVariante;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public pedido getPedido() {
        return pedido;
    }

    public void setPedido(pedido pedido) {
        this.pedido = pedido;
    }

    public productoVariante getProductoVariante() {
        return productoVariante;
    }

    public void setProductoVariante(productoVariante productoVariante) {
        this.productoVariante = productoVariante;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}