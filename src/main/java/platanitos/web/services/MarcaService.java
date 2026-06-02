package platanitos.web.services;

import org.springframework.stereotype.Service;
import platanitos.web.models.marca;
import platanitos.web.repositories.marcaRepository;

import java.util.List;

@Service
public class MarcaService {

    private final marcaRepository marcaRepo;

    public MarcaService(marcaRepository marcaRepo) {
        this.marcaRepo = marcaRepo;
    }

    public List<marca> listar() {
        return marcaRepo.findAll();
    }

    public List<marca> buscar(String nombre) {
        return marcaRepo.findByNombreContainingIgnoreCase(nombre);
    }

    public marca guardar(marca m) {
        return marcaRepo.save(m);
    }

    public marca actualizar(Long id, marca datos) {
        marca m = marcaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada: " + id));
        m.setNombre(datos.getNombre());
        m.setLogo_url(datos.getLogo_url());
        return marcaRepo.save(m);
    }

    public void eliminar(Long id) {
        long productos = marcaRepo.countProductosByMarcaId(id);
        if (productos > 0) throw new RuntimeException(
                "No se puede eliminar: tiene " + productos + " productos asociados.");
        marcaRepo.deleteById(id);
    }
}
