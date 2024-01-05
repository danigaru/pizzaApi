package pizzaapi.persitence.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import pizzaapi.persitence.entity.PizzaEntity;

public interface PizzaPagSortRepository extends ListPagingAndSortingRepository<PizzaEntity, Integer> {
}
