package org.hameister;

import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.hameister.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by hameister on 24.12.16.
 */
@Component
public class ItemInventory {

    private ItemRepository itemRepository;


    public ItemInventory(ItemRepository itemRepository) {
        Assert.notNull(itemRepository, "Repository must not be null!");
        this.itemRepository= itemRepository;

        for (Item item : itemRepository.findAll()) {
            System.out.println(item.getId()+":"+item.getDescription()+" "+ item.getLocation()+" "+item.getItemdate());
        }
    }
}
