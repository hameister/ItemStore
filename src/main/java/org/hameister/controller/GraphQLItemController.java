package org.hameister.controller;

import graphql.ExecutionResult;
import org.hameister.service.GraphQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hameister on 12.01.18.
 */
@RestController
public class GraphQLItemController {

    @Autowired
    GraphQLService graphQLService;

    @PostMapping(value = "/graphql/items")
    public ResponseEntity<Object> allItems(@RequestBody String query) {
        ExecutionResult result = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
