package com.walmart.web.model;

import java.util.List;

public class ItemQueryResult {
    private String maxId;
    private String nextPageMaxId;
    private String nextPage;
    private String totalPages;
    private String category;

    private List<Item> items;
    private List<List<Item>> splitedItems;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNextPageMaxId() {
        return nextPageMaxId;
    }

    public void setNextPageMaxId(String nextPageMaxId) {
        this.nextPageMaxId = nextPageMaxId;
    }

    public List<List<Item>> getSplitedItems() {
        return splitedItems;
    }

    public void setSplitedItems(List<List<Item>> splitedItems) {
        this.splitedItems = splitedItems;
    }
}
