package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platanitos.web.models.productoDetalle;
import java.util.List;

@Repository
public interface ProductoDetalleRepository extends JpaRepository<productoDetalle, Long> {
    List<productoDetalle> findByProductoId(Long productoId);
    void deleteByProductoId(Long productoId);
}
