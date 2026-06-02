package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import platanitos.web.models.pedidoItem;
import java.util.List;

@Repository
public interface PedidoItemRepository extends JpaRepository<pedidoItem, Long> {
    List<pedidoItem> findByPedidoId(Long pedidoId);

    @Query("""
        SELECT pv.producto.id, pv.producto.nombre, pv.producto.marca.nombre,
               SUM(pi.cantidad) as totalVentas
        FROM pedidoItem pi
        JOIN pi.productoVariante pv
        GROUP BY pv.producto.id, pv.producto.nombre, pv.producto.marca.nombre
        ORDER BY totalVentas DESC
        """)
    List<Object[]> findTopProductos(org.springframework.data.domain.Pageable pageable);
}
