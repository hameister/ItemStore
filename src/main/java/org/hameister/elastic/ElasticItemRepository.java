package org.hameister.elastic;

import org.hameister.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by hameister on 26.05.18.
 */
public interface ElasticItemRepository extends ElasticsearchRepository<Item, Long> {
}
