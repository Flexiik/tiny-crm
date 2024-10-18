package cz.fencl.minicrm.application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.fencl.minicrm.db.persistence.model.Item;
import cz.fencl.minicrm.db.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemRepository repository;

	// Get All
	@GetMapping
	public List<Item> getItems() {
		List<Item> customers = new ArrayList<>();
		repository.findAll().forEach(c -> customers.add(c));
		return customers;
	}

	@GetMapping("/{id}")
	public Item getItem(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}

	@PostMapping
	public Item saveItem(Item item) {
		return repository.save(item);
	}

	@PutMapping
	public Item updateItem(Item item) {
		return repository.save(item);
	}

	@DeleteMapping("/{id}")
	public void deleteItem(Item item) {
		repository.save(item);
	}
}
