package pizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pizzaapi.persitence.entity.OrderEntity;
import pizzaapi.persitence.projection.IOrderSummary;
import pizzaapi.persitence.repository.OrderRepository;
import pizzaapi.service.dto.RandomOrderDto;

import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SIDE = "S";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        return this.orderRepository.findAll();
    }

    public List<OrderEntity> getTodayOrders() {
        LocalDateTime today = LocalDate.now().atTime(0,0);

        System.out.println(today);
        return this.orderRepository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getOutSideOrders() {
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);

        return this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getCustomerOrders(String idCustomer) {
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    public IOrderSummary getOrderSummary(int orderId ) {
        return this.orderRepository.findOrderSummary(orderId);
    }

    @Transactional
    public boolean saveRandomOrder(RandomOrderDto orderDto) {
        return this.orderRepository.saveRandomOrder(orderDto.getIdCustomer(), orderDto.getMethod());
    }

}
