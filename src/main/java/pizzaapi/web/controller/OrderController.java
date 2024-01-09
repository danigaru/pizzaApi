package pizzaapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaapi.persitence.entity.OrderEntity;
import pizzaapi.persitence.projection.IOrderSummary;
import pizzaapi.service.OrderService;
import pizzaapi.service.dto.RandomOrderDto;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAll() {
        List<OrderEntity> items = this.orderService.getAll();
        items.forEach(orderEntity -> System.out.println(orderEntity.getCustomer().getName()));
        return ResponseEntity.ok(items);
    }

    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>> getTodayOrders() {
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }

    @GetMapping("/outside")
    public ResponseEntity<List<OrderEntity>> getOutsideOrders() {
        return ResponseEntity.ok(this.orderService.getOutSideOrders());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable String id) {
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }

    @GetMapping("/orderSummary/{id}")
    public ResponseEntity<IOrderSummary> getOrderSummary(@PathVariable int id) {
        return ResponseEntity.ok(this.orderService.getOrderSummary(id));
    }

    @PostMapping("/randomOrder")
    public ResponseEntity<?> randomOrder(@RequestBody RandomOrderDto orderDto) {
        try {
            return ResponseEntity.ok(this.orderService.saveRandomOrder(orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear orden aleatoria " + e.getMessage());
        }
    }
}
