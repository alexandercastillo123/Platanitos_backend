package platanitos.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.models.categoria;
import platanitos.web.services.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/admin/categorias")
@CrossOrigin(origins = "*")
public class categoriaController {

    private final CategoriaService categoriaService;

    public categoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listarActivas());
    }

    @GetMapping("/todas")
    public ResponseEntity<List<categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @GetMapping("/raices")
    public ResponseEntity<List<categoria>> listarRaices() {
        return ResponseEntity.ok(categoriaService.listarRaices());
    }
}
