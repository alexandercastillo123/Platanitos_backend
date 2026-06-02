package platanitos.web.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import platanitos.web.repositories.*;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class DashboardService {

    private final usuarioRepository usuarioRepo;
    private final ProductoRepository productoRepo;
    private final PedidoRepository pedidoRepo;
    private final PedidoItemRepository pedidoItemRepo;

    public DashboardService(usuarioRepository usuarioRepo,
                            ProductoRepository productoRepo,
                            PedidoRepository pedidoRepo,
                            PedidoItemRepository pedidoItemRepo) {
        this.usuarioRepo = usuarioRepo;
        this.productoRepo = productoRepo;
        this.pedidoRepo = pedidoRepo;
        this.pedidoItemRepo = pedidoItemRepo;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalClientes",      usuarioRepo.countByRol("cliente"));
        stats.put("productosActivos",   productoRepo.countByEstado("activo"));
        stats.put("productosAgotados",  productoRepo.countByEstado("agotado"));
        stats.put("totalPedidos",       pedidoRepo.count());
        stats.put("pedidosPendientes",  pedidoRepo.countByEstado("pendiente"));
        stats.put("pedidosEnviados",    pedidoRepo.countByEstado("enviado"));
        stats.put("ingresosTotales",    pedidoRepo.sumIngresosTotales());
        stats.put("ingresosUltimoMes",  pedidoRepo.sumIngresosDesde(
                OffsetDateTime.now().minusDays(30)));
        return stats;
    }

    public List<Map<String, Object>> getTopProductos() {
        List<Object[]> rows = pedidoItemRepo.findTopProductos(PageRequest.of(0, 5));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("productoId", row[0]);
            item.put("nombre",     row[1]);
            item.put("marca",      row[2]);
            item.put("ventas",     row[3]);
            result.add(item);
        }
        return result;
    }

    public List<Object> getUltimosPedidos() {
        return new ArrayList<>(pedidoRepo.findUltimos(PageRequest.of(0, 5)));
    }
}
