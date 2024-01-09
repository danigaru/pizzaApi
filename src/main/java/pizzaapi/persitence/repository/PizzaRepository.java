package pizzaapi.persitence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pizzaapi.persitence.entity.PizzaEntity;
import pizzaapi.service.dto.UpdatePizzaPriceDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {

    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);

    List<PizzaEntity> findTop5ByAvailableTrueAndPriceGreaterThanEqualOrderByPriceAsc(double price);

    int countAllByVeganTrue();

    @Query(value = "UPDATE pizza " +
            "SET price = :#{#pizzaPriceDto.price} " +
            "WHERE id_pizza = :#{#pizzaPriceDto.idPizza}", nativeQuery = true)
    @Modifying
    void updatePizzaPrice(@Param("pizzaPriceDto") UpdatePizzaPriceDto pizzaPriceDto );
}
