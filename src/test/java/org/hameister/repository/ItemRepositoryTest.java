package org.hameister.repository;

import org.hameister.elastic.ElasticConfiguration;
import org.hameister.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by hameister on 30.12.16.
 */
@RunWith(SpringRunner.class)
@Import(ElasticConfiguration.class)
@EnableJpaRepositories(basePackages = "org.hameister.repository")
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;


    @Autowired
    private ItemRepository repository;


    @Test
    public void findByLocationShouldReturnAListWithItemsAtALocation() throws Exception {
        this.entityManager.persist(new Item("Tasse", "Regal A", LocalDate.now()));
        List<Item> items = this.repository.findByLocation("Regal A");
        assertThat(items.get(0).getLocation(), is("Regal A"));
    }

}
