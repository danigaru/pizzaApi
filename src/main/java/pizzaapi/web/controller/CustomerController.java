package pizzaapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaapi.persitence.entity.CustomerEntity;
import pizzaapi.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    public final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerEntity> getByPhone(@RequestParam("phone") String phone) {
        return ResponseEntity.ok(this.customerService.findByPhone(phone));
    }

}
