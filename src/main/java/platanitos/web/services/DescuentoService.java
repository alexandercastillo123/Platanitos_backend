package platanitos.web.services;

import org.springframework.stereotype.Service;
import platanitos.web.models.descuento;
import platanitos.web.repositories.DescuentoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DescuentoService {

    private final DescuentoRepository descuentoRepo;

    public DescuentoService(DescuentoRepository descuentoRepo) {
        this.descuentoRepo = descuentoRepo;
    }

    public List<descuento> listar() {
        return descuentoRepo.findAll();
    }

    public List<descuento> listarActivos() {
        return descuentoRepo.findActivos(LocalDate.now());
    }

    public descuento guardar(descuento d) {
        return descuentoRepo.save(d);
    }

    public descuento actualizar(Long id, descuento datos) {
        descuento d = descuentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado: " + id));
        d.setNombre(datos.getNombre());
        d.setPorcentaje(datos.getPorcentaje());
        d.setFechainicio(datos.getFechainicio());
        d.setFechafin(datos.getFechafin());
        d.setActivo(datos.getActivo());
        return descuentoRepo.save(d);
    }

    public void eliminar(Long id) {
        descuentoRepo.deleteById(id);
    }
}
