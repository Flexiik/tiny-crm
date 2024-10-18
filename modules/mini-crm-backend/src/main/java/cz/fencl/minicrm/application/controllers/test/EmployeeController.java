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

import cz.fencl.minicrm.db.persistence.model.Employee;
import cz.fencl.minicrm.db.persistence.repositories.EmployeeRepository;

@RestController
@RequestMapping("/test/employee")
public class EmployeeController {
    @Autowired
    public EmployeeRepository repository;

    @GetMapping
    public Iterable<Employee> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findOne(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save(@RequestBody Employee employee) {

        return repository.save(employee);
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
