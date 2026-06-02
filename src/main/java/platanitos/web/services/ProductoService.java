package platanitos.web.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platanitos.web.models.*;
import platanitos.web.repositories.*;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final ProductoVarianteRepository varianteRepo;
    private final ProductoDetalleRepository detalleRepo;

    public ProductoService(ProductoRepository productoRepo,
                           ProductoVarianteRepository varianteRepo,
                           ProductoDetalleRepository detalleRepo) {
        this.productoRepo = productoRepo;
        this.varianteRepo = varianteRepo;
        this.detalleRepo  = detalleRepo;
    }


    public Page<producto> listar(String search, String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        boolean hasSearch = search != null && !search.isBlank();
        boolean hasEstado = estado != null && !estado.equals("todos");

        if (hasSearch && hasEstado)
            return productoRepo.findByNombreContainingIgnoreCaseAndEstado(search, estado, pageable);
        if (hasSearch)
            return productoRepo.findByNombreContainingIgnoreCase(search, pageable);
        if (hasEstado)
            return productoRepo.findByEstado(estado, pageable);
        return productoRepo.findAll(pageable);
    }

    public producto obtener(Long id) {
        return productoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public producto guardar(producto p) {
        if (p.getSlug() == null || p.getSlug().isBlank())
            p.setSlug(p.getNombre().toLowerCase()
                    .replaceAll("\\s+", "-")
                    .replaceAll("[^a-z0-9-]", ""));
        return productoRepo.save(p);
    }

    public producto actualizar(Long id, producto datos) {
        producto p = obtener(id);
        p.setNombre(datos.getNombre());
        p.setSlug(datos.getSlug());
        p.setMarca(datos.getMarca());
        p.setDescuento(datos.getDescuento());
        p.setPrecioBase(datos.getPrecioBase());
        p.setDescripcion(datos.getDescripcion());
        p.setUrlImagenPrincipal(datos.getUrlImagenPrincipal());
        p.setEstado(datos.getEstado());
        return productoRepo.save(p);
    }

    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }


    public java.util.Map<String, Object> getStats() {
        java.util.Map<String, Object> stats = new java.util.LinkedHashMap<>();
        stats.put("total",    productoRepo.count());
        stats.put("activos",  productoRepo.countByEstado("activo"));
        stats.put("agotados", productoRepo.countByEstado("agotado"));
        stats.put("stock",    productoRepo.sumStockTotal());
        return stats;
    }


    public List<productoVariante> getVariantes(Long productoId) {
        return varianteRepo.findByProductoId(productoId);
    }

    public productoVariante agregarVariante(Long productoId, productoVariante v) {
        producto p = obtener(productoId);
        v.setProducto(p);
        return varianteRepo.save(v);
    }

    public void eliminarVariante(Long varianteId) {
        varianteRepo.deleteById(varianteId);
    }


    public List<productoDetalle> getDetalles(Long productoId) {
        return detalleRepo.findByProductoId(productoId);
    }

    public productoDetalle agregarDetalle(Long productoId, productoDetalle d) {
        producto p = obtener(productoId);
        d.setProducto(p);
        return detalleRepo.save(d);
    }

    @Transactional
    public void eliminarDetalle(Long detalleId) {
        detalleRepo.deleteById(detalleId);
    }
}
