package platanitos.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platanitos.web.services.DashboardService;

import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
@CrossOrigin(origins = "*")
public class dashboardController {

    private final DashboardService dashboardService;

    public dashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @GetMapping("/top-productos")
    public ResponseEntity<?> getTopProductos() {
        return ResponseEntity.ok(dashboardService.getTopProductos());
    }

    @GetMapping("/ultimos-pedidos")
    public ResponseEntity<?> getUltimosPedidos() {
        return ResponseEntity.ok(dashboardService.getUltimosPedidos());
    }
}
