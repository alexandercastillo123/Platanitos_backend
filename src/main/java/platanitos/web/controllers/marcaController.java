package platanitos.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.models.marca;
import platanitos.web.services.MarcaService;

import java.util.List;

@RestController
@RequestMapping("/admin/marcas")
@CrossOrigin(origins = "*")
public class marcaController {

    private final MarcaService marcaService;

    public marcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<marca>> listar(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank())
            return ResponseEntity.ok(marcaService.buscar(search));
        return ResponseEntity.ok(marcaService.listar());
    }

    @PostMapping
    public ResponseEntity<marca> crear(@RequestBody marca m) {
        return ResponseEntity.ok(marcaService.guardar(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<marca> actualizar(@PathVariable Long id, @RequestBody marca m) {
        return ResponseEntity.ok(marcaService.actualizar(id, m));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
