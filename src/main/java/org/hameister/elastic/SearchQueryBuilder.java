package org.hameister.elastic;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hameister.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SearchQueryBuilder {


    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public SearchQueryBuilder(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }


    /*
    Query in Json format. Only description ans location are used for the search.
    SEARCHTEXT is can be for example: 'Tas Reg'
    This SEARCHTEXT will find all Tassen in Regals.
            {
          "bool" : {
            "should" : [ {
              "query_string" : {
                "query" : "SEARCHTEXT",
                "fields" : [ "description", "location" ],
                "lenient" : true
              }
            }, {
              "query_string" : {
                "query" : "*SEARCHTEXT*",
                "fields" : [ "description", "location" ],
                "lenient" : true
              }
            } ]
          }
        }
     */

    public List<Item> getAll(String text) throws IOException {

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(text)
                                .lenient(true)
                                .field("description")
                                .field("location")
                ).should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("description")
                        .field("location"));

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();


        return elasticsearchTemplate.queryForList(build, Item.class);
    }


}
