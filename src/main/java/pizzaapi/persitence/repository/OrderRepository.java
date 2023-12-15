package pizzaapi.persitence.repository;

import org.springframework.data.repository.ListCrudRepository;
import pizzaapi.persitence.entity.OrderEntity;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
}
