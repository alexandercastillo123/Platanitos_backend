package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platanitos.web.models.marca;
import java.util.List;

@Repository
public interface marcaRepository extends JpaRepository<marca, Long> {
    List<marca> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombre(String nombre);

    @Query("SELECT COUNT(p) FROM producto p WHERE p.marca.id = :marcaId")
    long countProductosByMarcaId(@Param("marcaId") Long marcaId);
}
