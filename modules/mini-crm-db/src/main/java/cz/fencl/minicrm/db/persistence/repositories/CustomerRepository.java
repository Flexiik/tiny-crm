package cz.fencl.minicrm.db.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.fencl.minicrm.db.persistence.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
