package org.llama.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.llama.challenge.domain.Snack;
import org.llama.challenge.domain.SnackOrder;
import org.llama.challenge.domain.SnackOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class SnackOrderService{
    public static String RESOURCE_URL = "http://45.55.41.44:8080";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private List<Snack> snackList;
    @Autowired
    private SnackOrder snackOrder;

    public List<SnackOrderItem> getSnackOrder(Integer orderValue){
        return snackOrder.createOrder(orderValue, snackList);
    }

    public List getAllStores() {
        ResponseEntity<String> response
                = restTemplate.getForEntity(RESOURCE_URL + "/stores", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List allStores = new ArrayList();
        try {
            String[] stores = mapper.readValue(response.getBody(), String[].class);
            allStores = Arrays.asList(stores);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allStores;
    }

    public List getAllSnacks(List<String> allStores) {
        snackList.clear();
        for(String store : allStores) {
            Snack[] responseObject = restTemplate.getForObject(RESOURCE_URL
                    + "/snackStore?name=" + store, Snack[].class);
            snackList.addAll(Arrays.asList(responseObject));
        }

        for(Snack snack : snackList) {
            Snack snackPref = restTemplate.getForObject(RESOURCE_URL
                    + "/llama?snack=" + snack.getName() , Snack.class);
            snack.preferenceScore(snackPref.getPreferenceScore());
        }
        return snackList;
    }

}