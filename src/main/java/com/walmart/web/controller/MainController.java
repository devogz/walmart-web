package com.walmart.web.controller;

import com.walmart.web.model.Item;
import com.walmart.web.model.ItemQueryResult;
import com.walmart.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Value("${app.welcome.title}")
    private String title = "";

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String defaultUrl(Model model) {
        addDefaultModelValues(model);

        ItemQueryResult result = itemService.getPaginatedItems();

        model.addAttribute("bookRows", result.getSplitedItems());
        model.addAttribute("nextPageMaxId", result.getNextPageMaxId());

        return "index";
    }

    @GetMapping("/books/{maxId}")
    public String books(Model model, @PathVariable(value = "maxId", required = false) String maxId) {
        addDefaultModelValues(model);

        ItemQueryResult result = itemService.getPaginatedItems(maxId);

        model.addAttribute("bookRows", result.getSplitedItems());
        model.addAttribute("nextPageMaxId", result.getNextPageMaxId());

        return "index";
    }

    @GetMapping("book/{itemId}")
    public String book(@PathVariable("itemId") String itemId, Model model) {
        addDefaultModelValues(model);

        Item result = itemService.getItemDetails(itemId);

        model.addAttribute("book", result);

        return "book";
    }

    private void addDefaultModelValues(Model model) {
        model.addAttribute("title", title);
    }

}