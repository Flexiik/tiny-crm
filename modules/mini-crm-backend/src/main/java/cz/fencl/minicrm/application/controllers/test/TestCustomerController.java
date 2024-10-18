package cz.fencl.minicrm.application.controllers.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.fencl.minicrm.db.persistence.model.Customer;
import cz.fencl.minicrm.db.persistence.repositories.CustomerRepository;

@RestController
@RequestMapping("/test/customer")
public class TestCustomerController {

    @Autowired
    public CustomerRepository repository;

//    @Autowired
//    public OrderRepository orderRepository;

    //Create customer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    //Get All
    @GetMapping
    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

//    //Create order with customer
//    @GetMapping("/order/{id}")
//    public Order createOrder(@PathVariable Customer customer) {
//        return orderRepository.save(customer);
//    }


    //Get customer from id
    @GetMapping("/{id}")
    public Customer findOne(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll(findAll());
    }
}
