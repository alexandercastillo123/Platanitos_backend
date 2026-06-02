package platanitos.web.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import platanitos.web.models.pedido;
import platanitos.web.models.pedidoItem;
import platanitos.web.repositories.PedidoItemRepository;
import platanitos.web.repositories.PedidoRepository;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final PedidoItemRepository itemRepo;

    public PedidoService(PedidoRepository pedidoRepo, PedidoItemRepository itemRepo) {
        this.pedidoRepo = pedidoRepo;
        this.itemRepo   = itemRepo;
    }

    public Page<pedido> listar(String search, String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaPedido").descending());
        boolean hasSearch = search != null && !search.isBlank();
        boolean hasEstado = estado != null && !estado.equals("todos");

        if (hasSearch && hasEstado) return pedidoRepo.buscarPorEstado(search, estado, pageable);
        if (hasSearch)              return pedidoRepo.buscar(search, pageable);
        if (hasEstado)              return pedidoRepo.findByEstado(estado, pageable);
        return pedidoRepo.findAll(pageable);
    }

    public pedido obtener(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    public List<pedidoItem> getItems(Long pedidoId) {
        return itemRepo.findByPedidoId(pedidoId);
    }

    public pedido cambiarEstado(Long id, String nuevoEstado) {
        pedido p = obtener(id);

        Map<String, List<String>> transiciones = Map.of(
                "pendiente",  List.of("pagado", "cancelado"),
                "pagado",     List.of("enviado", "cancelado"),
                "enviado",    List.of("entregado"),
                "entregado",  List.of(),
                "cancelado",  List.of()
        );

        List<String> permitidos = transiciones.getOrDefault(p.getEstado(), List.of());
        if (!permitidos.contains(nuevoEstado))
            throw new RuntimeException("Transición inválida: " + p.getEstado() + " → " + nuevoEstado);

        p.setEstado(nuevoEstado);
        return pedidoRepo.save(p);
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("total",       pedidoRepo.count());
        stats.put("pendientes",  pedidoRepo.countByEstado("pendiente"));
        stats.put("enviados",    pedidoRepo.countByEstado("enviado"));
        stats.put("ingresos",    pedidoRepo.sumIngresosTotales());
        stats.put("ingresosMes", pedidoRepo.sumIngresosDesde(OffsetDateTime.now().minusDays(30)));
        return stats;
    }
}
