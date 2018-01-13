package org.hameister.service;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hameister on 12.01.18.
 */
@Component
public class ItemListDataFetcher implements DataFetcher<List<Item>> {


    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return itemRepository.findAll();
    }
}
