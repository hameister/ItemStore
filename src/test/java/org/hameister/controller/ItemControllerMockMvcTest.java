package org.hameister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hameister.ItemStoreApplication;
import org.hameister.model.Item;
import org.hameister.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created by hameister on 29.12.16.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ItemStoreApplication.class)
public class ItemControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;


    /*

    Man ruft die REST-Schnittstelle auf und der Aufruf wird an den Service-Mopck weitergeleitet.
    Findet nur ein Delegate statt, kann man über den Sinn diskutieren, aber es wird auf alle
    Faelle das Verhalten der REST-Schnittstelle überprüft und definiert.
     */
    @Test
    public void getItemAtALocationShouldReturnAllItemsAtThisLocation() throws Exception {

        String expectedItemDescription = "Teller";

        given(this.itemService.getItemsByLocation("Regal A"))
                .willReturn(Arrays.asList(new Item(1l,"Teller", "Regal A", LocalDate.now())));

        // Testen von JsonPath
        // http://jsonpath.com

        this.mvc.perform(get("/item/Regal A")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(expectedItemDescription));
    }

    @Test
    public void getItemsAtALocationShouldReturnAllItemsAtThisLocation() throws Exception {

        String expectedItemDescription1 = "Teller";
        String expectedItemDescription2 = "Untertasse";

        given(this.itemService.getItemsByLocation("Regal A"))
                .willReturn(Arrays.asList(new Item(1l,"Teller", "Regal A", LocalDate.now()),
                        new Item(1l,"Untertasse", "Regal A", LocalDate.now())));

        // Testen von JsonPath
        // http://jsonpath.com

        this.mvc.perform(get("/item/Regal A").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(expectedItemDescription1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value(expectedItemDescription2));
    }

    @Test
    public void updateShouldReturnTheUpdatedItemAnd200() throws Exception {
        Item item = new Item(1l,"Teller", "Regal A", null);

        given(this.itemService.update(any(Item.class))).willReturn(item);

        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(item);

        this.mvc.perform(put("/item", item)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value(item.getLocation()));
    }

    @Test
    public void createItem() throws Exception {
        Item item = new Item(1l,"Teller", "Regal A", null);
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(item);

        String expectedItemDescription = "Teller";
        when(itemService.create(any(Item.class))).thenReturn(item);

        this.mvc.perform(post("/item", item)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedItemDescription));

    }


}
