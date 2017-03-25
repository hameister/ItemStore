package org.hameister.service;

import org.hameister.model.Item;
import org.hameister.repository.ItemRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by hameister on 25.12.16.
 */
public class ItemServiceTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Test
    public void getAllItemsWithPrefixedLocationShouldReturnListOfItemsWhereTheLocationIsPrefixed() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);
        String prefix = "==> ";

        Item item = new Item("Tasse", "Regal A", LocalDate.now());

        List<Item> items = Arrays.asList(item);
        when(itemRepository.findAll()).thenReturn(items);

        assertThat(itemService.getAllItemsWithPrefixedLocation(prefix).get(0).getLocation(), is(prefix + "Regal A"));
    }

    @Test
    public void createShouldCallSaveItemOnceIfIdIsNull() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        Item item = new Item();
        itemService.create(item);

        verify(itemRepository, times(1)).save(item);

    }

    @Test
    public void getItemListForLocationShouldWorkForAllLOcations() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        List<Item> expextedItemList = new ArrayList<>();

        List<Item> items = itemService.getItemsByLocation(anyString());
        when(itemRepository.findByLocation(anyString())).thenReturn(expextedItemList);
        assertThat(items, is(expextedItemList));

    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionIfIdIsNotNull() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        Item item = new Item();
        item.setId(1l);
        itemService.create(item);
    }


    // With @Rule
    @Test
    public void createShouldThrowExceptionWithMessageIfIdIsNotNull() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        Item item = new Item();
        item.setId(1l);

        this.thrownException.expect(IllegalArgumentException.class);
        this.thrownException.expectMessage("id in Item must be null");
        itemService.create(item);

    }


    @Test
    public void caputureLocationNameParameter() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        String expectedLocationName = "Regal A";
        ArgumentCaptor<String> locationCature =
                ArgumentCaptor.forClass(String.class);

        itemService.getItemsByLocation("A");

        // Speichere den Wert, der beim Aufruf von findByLocation benutzt wird
        verify(itemRepository, times(1)).findByLocation(locationCature.capture());

        assertThat(locationCature.getValue(), is(expectedLocationName));
    }
}
