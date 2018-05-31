package org.hameister.elastic;

import org.hameister.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by hameister on 26.05.18.
 */
@RestController
@RequestMapping("/item/elastic")
public class ElasticItemController {

    @Autowired
    private SearchQueryBuilder searchQueryBuilder;

    @GetMapping(value = "/{text}")
    public List<Item> getAll(@PathVariable final String text) {
        try {
            return searchQueryBuilder.getAll(text);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
