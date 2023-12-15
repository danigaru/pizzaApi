package pizzaapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaapi.persitence.entity.PizzaEntity;
import pizzaapi.service.PizzaService;

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
    public ResponseEntity<List<PizzaEntity>> getAll() {
        return ResponseEntity.ok(pizzaService.getAll());
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getPizza(@PathVariable int idPizza) {
        return ResponseEntity.ok(pizzaService.getPizza(idPizza));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getByAvailable() {
        System.out.println(this.pizzaService.getByAvailable().size());
        return ResponseEntity.ok(this.pizzaService.getByAvailable());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.pizzaService.getByName(name));
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