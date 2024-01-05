package pizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pizzaapi.persitence.entity.PizzaEntity;
import pizzaapi.persitence.repository.PizzaPagSortRepository;
import pizzaapi.persitence.repository.PizzaRepository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int size) {
        //return jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));

        PageRequest pageRequest = PageRequest.of(page, size);
        return  this.pizzaPagSortRepository.findAll(pageRequest);
    }

    public PizzaEntity getPizza(int idPizza) {
        return this.pizzaRepository.findById(idPizza).orElse( null);
    }

    public List<PizzaEntity> getByAvailable() {
        System.out.println(this.pizzaRepository.countAllByVeganTrue());
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name)
                    .orElseThrow( () -> new RuntimeException("La pizza no existe"));
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    public boolean existPizza(int idPizza) {
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(int idPizza) {
        this.pizzaRepository.deleteById(idPizza);
    }

    public List<PizzaEntity> getAllWithDescription(String description) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(description);
    }

    public List<PizzaEntity> getAllWithout(String description) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(description);
    }

    public List<PizzaEntity>  getCheapest(double price) {
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public List<PizzaEntity> getGreater(double price) {
        return this.pizzaRepository.findTop5ByAvailableTrueAndPriceGreaterThanEqualOrderByPriceAsc(price);
    }
}
