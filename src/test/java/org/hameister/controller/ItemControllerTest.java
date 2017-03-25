package org.hameister.controller;

import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.hameister.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.slf4j.Logger;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


/**
 * Created by hameister on 25.12.16.
 */
public class ItemControllerTest {

    Logger logger;

    @InjectMocks
    ItemService itemService;

    @Before
    public void before() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
        logger = mock(Logger.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getItemAtLocationShouldReturnItemsAtALocation() {
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);

        List<Item> expectedItems = mock(List.class);
        when(itemService.getItemsByLocation(anyString())).thenReturn(expectedItems);
        List<Item>  items = itemController.getItemAtLocation(anyString()).getBody();

        assertThat(expectedItems, is(items));
    }

    @Test
    public void updateItemWithChangeLocationShouldLogTheLocationOfTheItem() {
        ItemController itemController = new ItemController(itemService);
        Item item = new Item();
        item.setId(1l);
        item.setLocation("changedLocation");

        ArgumentCaptor<String> expectedLogEntry = ArgumentCaptor.forClass(String.class);

        itemController.updateItem(item);

        // Speichere den Wert, der beim Aufruf von debug verwendet wird
        verify(logger, times(1)).debug(expectedLogEntry.capture());
        // Welcher Wert wurde durch den Logger geschrieben
        assertThat(expectedLogEntry.getValue(), is("changedLocation"));
    }

    @Test
    public void  deleteItemShouldDeleteAnItemWithId() {
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);
        Item item = new Item();
        item.setId(1l);

        Whitebox.setInternalState(itemController,"testFlag", true);
        itemController.deleteItem(anyLong());
        verify(itemService, times(1)).delete(anyLong());

    }
}
