package platanitos.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.models.*;
import platanitos.web.services.ProductoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/productos")
@CrossOrigin(origins = "*")
public class productoController {

    private final ProductoService productoService;

    public productoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ── Producto ──────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<Page<producto>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productoService.listar(search, estado, page, size));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(productoService.getStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<producto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<producto> crear(@RequestBody producto p) {
        return ResponseEntity.ok(productoService.guardar(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<producto> actualizar(@PathVariable Long id, @RequestBody producto p) {
        return ResponseEntity.ok(productoService.actualizar(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ── Variantes ─────────────────────────────────────────────

    @GetMapping("/{id}/variantes")
    public ResponseEntity<List<productoVariante>> getVariantes(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getVariantes(id));
    }

    @PostMapping("/{id}/variantes")
    public ResponseEntity<productoVariante> agregarVariante(
            @PathVariable Long id, @RequestBody productoVariante v) {
        return ResponseEntity.ok(productoService.agregarVariante(id, v));
    }

    @DeleteMapping("/{id}/variantes/{varianteId}")
    public ResponseEntity<Void> eliminarVariante(
            @PathVariable Long id, @PathVariable Long varianteId) {
        productoService.eliminarVariante(varianteId);
        return ResponseEntity.noContent().build();
    }

    // ── Detalles ──────────────────────────────────────────────

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<productoDetalle>> getDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getDetalles(id));
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<productoDetalle> agregarDetalle(
            @PathVariable Long id, @RequestBody productoDetalle d) {
        return ResponseEntity.ok(productoService.agregarDetalle(id, d));
    }

    @DeleteMapping("/{id}/detalles/{detalleId}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable Long id, @PathVariable Long detalleId) {
        productoService.eliminarDetalle(detalleId);
        return ResponseEntity.noContent().build();
    }
}
