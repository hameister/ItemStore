package org.hameister.controller;

import org.hameister.model.Item;
import org.hameister.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by hameister on 24.12.16.
 */
@RestController
public class ItemController {

    private ItemService itemService;

    private  boolean testFlag;

    @Autowired
    public ItemController(ItemService itemService) {
        Assert.notNull(itemService, "Service must not be null!");
        this.itemService = itemService;
    }

    @GetMapping(value = "/item")
    ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<List<Item>>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping(value = "/item/{location}")
    ResponseEntity<List<Item>> getItemAtLocation(@PathVariable("location") String location) {
        return new ResponseEntity<List<Item>>(itemService.getItemsByLocation(location), HttpStatus.OK);
    }

    @PostMapping(value = "/item")
    ResponseEntity<Item> createItem(@RequestBody Item item) {
        return new ResponseEntity<Item>(itemService.create(item), HttpStatus.CREATED);

    }

    @PutMapping(value = "/item")
    ResponseEntity<Item> updateItem(@RequestBody Item item) {

        if (item.getId() == null) {
            return new ResponseEntity<Item>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Item updatedItem = itemService.update(item);

        if (updatedItem == null) {
            return new ResponseEntity<Item>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Item>(updatedItem, HttpStatus.OK);
    }


    @DeleteMapping(value = "/item/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable("id") long id) {
        if(testFlag) {
            itemService.delete(id);
        }

        return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/item/{id}/Location.html", produces = MediaType.TEXT_HTML_VALUE)
    public String getLocationOfItemWithIdHTML(@PathVariable("id") long id) {

        Item item =itemService.getItemWithId(id);
        String html = "<html><body><h2>" + item.getLocation() + "</h2></body></html>";

        return html;
    }


    @GetMapping(value = "/item/{id}/id")
    public ResponseEntity<Item> getItemWithId(@PathVariable("id") long id) {
        Item item =itemService.getItemWithId(id);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }


}
