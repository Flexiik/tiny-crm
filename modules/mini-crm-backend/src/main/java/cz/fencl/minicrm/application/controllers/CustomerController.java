package cz.fencl.minicrm.application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cz.fencl.minicrm.application.model.CustomerDto;
import cz.fencl.minicrm.db.persistence.model.Customer;
import cz.fencl.minicrm.db.persistence.repositories.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private Gson gson = new Gson();

	@Autowired
	private CustomerRepository repository;

	// Get All
	@GetMapping
	public List<CustomerDto> getCustomers() {
		List<CustomerDto> customers = new ArrayList<>();
		repository.findAll().forEach(c -> customers.add(toDto(c)));
		return customers;
	}

	@GetMapping("/{id}")
	public CustomerDto getCustomer(@PathVariable Long id) {
		return toDto(repository.findById(id).orElse(null));
	}

	private CustomerDto toDto(Customer customer) {
		return gson.fromJson(gson.toJson(customer), CustomerDto.class);
	}
}
