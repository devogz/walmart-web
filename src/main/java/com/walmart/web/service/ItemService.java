package com.walmart.web.service;

import com.walmart.web.model.Item;
import com.walmart.web.model.ItemQueryResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class ItemService {

    @Value("${api.url}")
    private String walmartApiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.path.paginated}")
    private String paginatedItemsPath;

    @Value("${api.path.details}")
    private String itemDetailsPath;

    private static final String BOOK_CATEGORY_ID = "3920";
    public static final int ITEM_COUNT_PER_PAGE = 20;
    public static final int ITEM_COUNT_PER_ROW = 4;

    private static RestTemplate restTemplate = new RestTemplate();

    private URI getPagenitedItemsURI(String maxId) {
        UriComponentsBuilder allItemsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(walmartApiUrl)
                .path(paginatedItemsPath)
                .queryParam("apiKey", apiKey)
                .queryParam("category", BOOK_CATEGORY_ID)
                .queryParam("count", ITEM_COUNT_PER_PAGE);

        if (StringUtils.hasText(maxId)) {
            allItemsBuilder.queryParam("maxId", maxId);
        }

        return allItemsBuilder.build().toUri();
    }

    private URI getItemDetailsURI(String itemId) {
        Assert.notNull(itemId, "itemId can not be null");

        UriComponentsBuilder allItemsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(walmartApiUrl)
                .path(itemDetailsPath)
                .queryParam("apiKey", apiKey)
                .queryParam("itemId", itemId);

        return allItemsBuilder.build().toUri();
    }

    private List<List<Item>> splitList(List<Item> list, int listCount) {
        List<List<Item>> resultList = new ArrayList<>();
        ArrayList<Item> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i%listCount == 0) {
                if (subList != null) {
                    resultList.add(subList);
                }
                subList = new ArrayList<>();
            }
            subList.add(list.get(i));
            if (i == list.size() - 1) {
                resultList.add(subList);
            }
        }
        return resultList;
    }

    public ItemQueryResult getPaginatedItems() {
        return getPaginatedItems(null);
    }

    public ItemQueryResult getPaginatedItems(String maxId) {
        URI uri = getPagenitedItemsURI(maxId);

        ItemQueryResult result = restTemplate.getForObject(uri, ItemQueryResult.class);
        result.setSplitedItems(splitList(result.getItems(), ITEM_COUNT_PER_ROW));
        result.setNextPageMaxId(getQueryMap(result.getNextPage()).get("maxId"));

        return result;
    }

    private static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    public Item getItemDetails(String maxId) {
        URI uri = getItemDetailsURI(maxId);

        ItemQueryResult itemQueryResult = restTemplate.getForObject(uri, ItemQueryResult.class);

        if (!CollectionUtils.isEmpty(itemQueryResult.getItems())) {
            return itemQueryResult.getItems().get(0);
        }

        return null;
    }
}
