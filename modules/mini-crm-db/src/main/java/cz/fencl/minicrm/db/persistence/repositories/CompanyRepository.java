package cz.fencl.minicrm.db.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.fencl.minicrm.db.persistence.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
