package cz.fencl.minicrm.db.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.fencl.minicrm.db.persistence.model.Contract;

public interface ContractRepository extends CrudRepository<Contract, Long> {
}
