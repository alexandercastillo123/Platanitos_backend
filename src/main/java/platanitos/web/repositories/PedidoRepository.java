package platanitos.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platanitos.web.models.pedido;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<pedido, Long> {

    Page<pedido> findByEstado(String estado, Pageable pageable);

    @Query("""
        SELECT p FROM pedido p
        WHERE CAST(p.id AS string) LIKE %:q%
           OR LOWER(p.usuario.nombres) LIKE LOWER(CONCAT('%',:q,'%'))
           OR LOWER(p.usuario.email)   LIKE LOWER(CONCAT('%',:q,'%'))
        """)
    Page<pedido> buscar(@Param("q") String q, Pageable pageable);

    @Query("""
        SELECT p FROM pedido p
        WHERE p.estado = :estado
          AND (CAST(p.id AS string) LIKE %:q%
           OR LOWER(p.usuario.nombres) LIKE LOWER(CONCAT('%',:q,'%'))
           OR LOWER(p.usuario.email)   LIKE LOWER(CONCAT('%',:q,'%')))
        """)
    Page<pedido> buscarPorEstado(@Param("q") String q, @Param("estado") String estado, Pageable pageable);

    long countByEstado(String estado);

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM pedido p WHERE p.estado <> 'cancelado'")
    BigDecimal sumIngresosTotales();

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM pedido p WHERE p.estado <> 'cancelado' AND p.fechaPedido >= :desde")
    BigDecimal sumIngresosDesde(@Param("desde") OffsetDateTime desde);

    @Query("SELECT p FROM pedido p ORDER BY p.fechaPedido DESC")
    List<pedido> findUltimos(Pageable pageable);
}
