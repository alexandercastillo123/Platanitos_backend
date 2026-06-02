package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platanitos.web.models.productoVariante;
import java.util.List;

@Repository
public interface ProductoVarianteRepository extends JpaRepository<productoVariante, Long> {
    List<productoVariante> findByProductoId(Long productoId);
    void deleteByProductoId(Long productoId);

    @Query("SELECT COUNT(pv) FROM productoVariante pv WHERE pv.producto.id = :productoId")
    long countByProductoId(@Param("productoId") Long productoId);
}
