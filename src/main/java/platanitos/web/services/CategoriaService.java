package platanitos.web.services;

import org.springframework.stereotype.Service;
import platanitos.web.models.categoria;
import platanitos.web.repositories.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public List<categoria> listar() {
        return categoriaRepo.findAll();
    }

    public List<categoria> listarActivas() {
        return categoriaRepo.findByActiva(true);
    }

    public List<categoria> listarRaices() {
        return categoriaRepo.findByPadreIsNull();
    }
}
