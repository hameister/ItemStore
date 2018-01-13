package org.hameister.service;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hameister on 12.01.18.
 */
@Component
public class ItemDataFetcher implements DataFetcher<Item> {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return itemRepository.findOne(id);

    }
}
