package platanitos.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platanitos.web.models.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {
    long countByRol(String rol);
}
