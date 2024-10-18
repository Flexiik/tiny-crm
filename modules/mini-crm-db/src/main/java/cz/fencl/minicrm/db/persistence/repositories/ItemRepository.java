package cz.fencl.minicrm.db.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.fencl.minicrm.db.persistence.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
