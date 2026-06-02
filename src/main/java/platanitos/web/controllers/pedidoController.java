package platanitos.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.models.pedido;
import platanitos.web.models.pedidoItem;
import platanitos.web.services.PedidoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/pedidos")
@CrossOrigin(origins = "*")
public class pedidoController {

    private final PedidoService pedidoService;

    public pedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<Page<pedido>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(pedidoService.listar(search, estado, page, size));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(pedidoService.getStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<pedido> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtener(id));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<pedidoItem>> getItems(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.getItems(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<pedido> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, nuevoEstado));
    }
}
