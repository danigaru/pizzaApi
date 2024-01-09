package pizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizzaapi.persitence.entity.PizzaEntity;
import pizzaapi.persitence.repository.PizzaPagSortRepository;
import pizzaapi.persitence.repository.PizzaRepository;
import pizzaapi.service.dto.UpdatePizzaPriceDto;
import pizzaapi.service.exception.EmailApiException;

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

    public Page<PizzaEntity> getByAvailable(int page, int size, String sortBy, String sortDirection) {
        System.out.println(this.pizzaRepository.countAllByVeganTrue());

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
        //return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name)
                    .orElseThrow( () -> new RuntimeException("La pizza no existe"));
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDto pizzaPriceDto) {
        this.pizzaRepository.updatePizzaPrice(pizzaPriceDto);
        this.sendEmail();
    }

    private void sendEmail() {
        throw new EmailApiException();
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
