package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platanitos.web.models.categoria;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<categoria, Long> {
    List<categoria> findByActiva(Boolean activa);
    List<categoria> findByPadreIsNull();
}
