package org.hameister.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hameister.ItemStoreApplication;
import org.hameister.elastic.ElasticConfiguration;
import org.hameister.model.Item;
import org.hameister.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

/**
 * Created by hameister on 16.01.17.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class ItemControllerJsonTest {

    @Autowired
    private JacksonTester<Item> json;


    @Test
    public void JsonResponseShouldContainLocation() throws IOException {
        Item item = new Item(1l,"Teller", "Regal A", LocalDate.now());

        JsonContent<Item> write = this.json.write(item);

        this.json.write(item).assertThat()
                .extractingJsonPathStringValue("@.location")
                .isEqualTo("Regal A");
        this.json.write(item).assertThat()
                .extractingJsonPathStringValue("@.description")
                .isEqualTo("Teller");
        this.json.write(item).assertThat()
                .extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);
        this.json.write(item).assertThat()
                .extractingJsonPathNumberValue("@.itemdate")
                .isNull();

    }
}
