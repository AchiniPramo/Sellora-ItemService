package lk.ijse.eca.itemservice.repository;

import lk.ijse.eca.itemservice.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
