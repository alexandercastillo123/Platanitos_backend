package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import platanitos.web.models.descuento;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DescuentoRepository extends JpaRepository<descuento, Long> {

    @Query("SELECT d FROM descuento d WHERE d.activo = true AND d.fechainicio <= :hoy AND d.fechafin >= :hoy")
    List<descuento> findActivos(LocalDate hoy);
}
