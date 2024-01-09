package pizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizzaapi.persitence.entity.CustomerEntity;
import pizzaapi.persitence.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity findByPhone(String phone) {
        return this.customerRepository.findByPhone(phone);
    }
}
