package org.hameister.elastic;

import org.hameister.model.Item;
import org.hameister.elastic.ElasticItemRepository;
import org.hameister.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Created by hameister on 26.05.18.
 */
@Component
public class ItemMigrator {

    ElasticsearchOperations operations;
    ElasticItemRepository elasticUsersRepository;
    ItemRepository repository;

    @Autowired
    public ItemMigrator(ItemRepository jpaRepository, ElasticsearchTemplate operations, ElasticItemRepository elasticUsersRepository) {
        this.repository = jpaRepository;
        this.operations = operations;
        this.elasticUsersRepository = elasticUsersRepository;
    }

    @PostConstruct
    @Transactional
    public void loadAll() {
        Iterable<Item> items = repository.findAll();
        operations.putMapping(Item.class);
        elasticUsersRepository.save(items);


    }

}
