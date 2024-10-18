package cz.fencl.minicrm.db.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.fencl.minicrm.db.persistence.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
