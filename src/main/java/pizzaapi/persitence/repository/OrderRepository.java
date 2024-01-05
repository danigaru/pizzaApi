package pizzaapi.persitence.repository;

import org.springframework.data.repository.ListCrudRepository;
import pizzaapi.persitence.entity.OrderEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByDateAfter(LocalDateTime date);

    List<OrderEntity> findAllByMethodIn(List<String> methods);

}
