package org.hameister.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hameister.model.Item;
import org.hameister.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by hameister on 22.01.17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerHtmlTest {



    @Autowired
    private WebClient webClient;

    @MockBean
    private ItemService itemService;


    @Test
    public void shouldReturnTheHtmlLocationOfAnItem() throws Exception {
        given(this.itemService.getItemWithId(1l))
                .willReturn(new Item("Tasse", "Regal A", null));
        HtmlPage page = this.webClient.getPage("/item/1/Location.html");
        assertThat(page.getBody().getTextContent()).isEqualTo("Regal A");
    }
}
