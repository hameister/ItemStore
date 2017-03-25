package org.hameister.service;

import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


/**
 * Created by hameister on 24.12.16.
 */
@Service
public class ItemService {

    public Logger logger = LoggerFactory.getLogger(ItemService.class);

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        Assert.notNull(itemRepository, "Repository must not be null!");
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public List<Item> getAllItemsWithPrefixedLocation(String prefix) {
        List<Item> items = itemRepository.findAll();
        items.forEach(item ->  item.setLocation(prefix+item.getLocation()));
        return items;
    }

    public List<Item> getItemsByLocation(String locationName) {
        return itemRepository.findByLocation("Regal "+computeLocation(locationName));
    }

    public Item getItemWithId(Long id) {

        return itemRepository.findOne(id);
    }

    private String computeLocation(String locationName) {
        return locationName.toUpperCase();
    }

    public Item create(Item item) throws IllegalArgumentException {
        if (item.getId() != null) {
            throw new IllegalArgumentException("id in Item must be null");
        }
        return itemRepository.save(item);
    }

    public Item update(Item todo) {
        logger.debug(todo.getLocation());

        if (itemRepository.findOne(todo.getId()) == null) {
            return null;
        }
        return itemRepository.save(todo);
    }

    public void delete(long id) {
        itemRepository.delete(id);
    }
}
