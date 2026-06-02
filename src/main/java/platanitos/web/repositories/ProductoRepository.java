package platanitos.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import platanitos.web.models.producto;

@Repository
public interface ProductoRepository extends JpaRepository<producto, Long> {

    Page<producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Page<producto> findByEstado(String estado, Pageable pageable);
    Page<producto> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado, Pageable pageable);

    long countByEstado(String estado);

    @Query("SELECT COALESCE(SUM(pv.stock), 0) FROM productoVariante pv")
    Long sumStockTotal();
}
