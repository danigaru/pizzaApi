package pizzaapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaapi.persitence.entity.PizzaEntity;
import pizzaapi.service.PizzaService;
import pizzaapi.service.dto.UpdatePizzaPriceDto;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        return ResponseEntity.ok(pizzaService.getAll(page, size));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getPizza(@PathVariable int idPizza) {
        return ResponseEntity.ok(pizzaService.getPizza(idPizza));
    }

        @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getByAvailable(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "price") String sortBy,
                                                            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(this.pizzaService.getByAvailable(page, size, sortBy, sortDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/description/{description}")
    public ResponseEntity<List<PizzaEntity>> getAllWithDescription(@PathVariable String description) {
        return ResponseEntity.ok(this.pizzaService.getAllWithDescription(description));
    }

    @GetMapping("/without/description/{description}")
    public ResponseEntity<List<PizzaEntity>> getAllWithoutDescription(@PathVariable String description) {
        return ResponseEntity.ok(this.pizzaService.getAllWithout(description));
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable double price) {
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    @GetMapping("/greater/{price}")
    public ResponseEntity<List<PizzaEntity>> getGreater(@PathVariable double price) {
        return ResponseEntity.ok(this.pizzaService.getGreater(price));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PizzaEntity pizza) {
        try {
            if (pizza.getIdPizza() == null || !pizzaService.existPizza(pizza.getIdPizza())) {
                return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.save(pizza));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inconvenientes al guardar datos");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inconvenites al guardar datos  " + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody PizzaEntity pizza) {
        try {
            if (pizza.getIdPizza() != null && pizzaService.existPizza(pizza.getIdPizza())) {
                return ResponseEntity.ok(pizzaService.save(pizza));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pizza no existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al editar datos" + e.getMessage());
        }

    }

    @PutMapping("/updatePrice")
    public ResponseEntity<?> updatePrice(@RequestBody UpdatePizzaPriceDto pizzaPriceDto) {
        try {

            if(this.pizzaService.existPizza(pizzaPriceDto.getIdPizza())) {
                this.pizzaService.updatePrice(pizzaPriceDto);
                return ResponseEntity.status(HttpStatus.OK).body("Precio actualizado correctamente");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pizza no existe");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar precio " + e.getMessage());
        }
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<?> delete(@PathVariable int idPizza) {
        try {
            if (pizzaService.existPizza(idPizza)) {
                pizzaService.delete(idPizza);
                return ResponseEntity.status(HttpStatus.OK).body("LA PIZZA SE ELIMINÓ CON ÉXITO");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pizza no existe");

        } catch (Exception e) {
           return ResponseEntity.internalServerError().build();
        }
    }
}