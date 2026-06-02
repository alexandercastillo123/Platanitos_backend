package platanitos.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.models.descuento;
import platanitos.web.services.DescuentoService;

import java.util.List;

@RestController
@RequestMapping("/admin/descuentos")
@CrossOrigin(origins = "*")
public class descuentoController {

    private final DescuentoService descuentoService;

    public descuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @GetMapping
    public ResponseEntity<List<descuento>> listar() {
        return ResponseEntity.ok(descuentoService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<descuento>> listarActivos() {
        return ResponseEntity.ok(descuentoService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<descuento> crear(@RequestBody descuento d) {
        return ResponseEntity.ok(descuentoService.guardar(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<descuento> actualizar(@PathVariable Long id, @RequestBody descuento d) {
        return ResponseEntity.ok(descuentoService.actualizar(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        descuentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
