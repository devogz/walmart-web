package com.walmart.web;

import com.walmart.web.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

	@Autowired
	private WebApplicationContext wac;

    private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

    @Test
    public void testIndex() throws Exception{
        int rowCount = ItemService.ITEM_COUNT_PER_PAGE / ItemService.ITEM_COUNT_PER_ROW;
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("bookRows", hasSize(rowCount)));
    }

    @Test
    public void testBookDetails() throws Exception{
	    String exampleBookId = "189642";
        int rowCount = ItemService.ITEM_COUNT_PER_PAGE / ItemService.ITEM_COUNT_PER_ROW;
        this.mockMvc.perform(get("/book/" + exampleBookId))
                .andExpect(status().isOk())
                .andExpect(view().name("book"));
    }
}
