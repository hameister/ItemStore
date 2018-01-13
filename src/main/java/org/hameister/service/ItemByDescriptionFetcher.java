package org.hameister.service;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hameister on 13.01.18.
 */
@Component
public class ItemByDescriptionFetcher implements DataFetcher<List<Item>>{

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public List<Item> get(DataFetchingEnvironment environment) {
        String description = environment.getArgument("description");
        return itemRepository.findByDescription(description);
    }
}
