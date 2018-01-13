package org.hameister.repository;

import org.hameister.model.Item;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by hameister on 24.12.16.
 */
@org.springframework.stereotype.Repository
public interface ItemRepository extends Repository<Item, Long> {
    Item findOne(Long id);
    List<Item> findAll();
    List<Item> findByLocation(String location);
    List<Item> findByDescription(String description);
    Item save(Item item);
    void delete(Long id);
}
