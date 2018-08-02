package org.llama.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.llama.challenge.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;

@SpringBootApplication(scanBasePackages = {"org.llama.challenge.domain",
                                           "org.llama.challenge.service",
                                           "org.llama.challenge.web"})
public class SnackOrderApplication {

    private static final Logger log = LoggerFactory.getLogger(SnackOrderApplication.class);

    public static void main(String[] args){
        SpringApplication.run(SnackOrderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String resourceUrl = "http://45.55.41.44:8080";
            ResponseEntity<String> response
                    = restTemplate.getForEntity(resourceUrl + "/stores", String.class);

            ObjectMapper mapper = new ObjectMapper();
            String[] stores = mapper.readValue(response.getBody(), String[].class);

            List<Snack> snackList = new ArrayList<Snack>();
            for(String store : stores) {
                Snack[] responseObject = restTemplate.getForObject(resourceUrl
                                + "/snackStore?name=" + store, Snack[].class);
                snackList.addAll(Arrays.asList(responseObject));
            }

            for(Snack snack : snackList) {
                Snack snackPref = restTemplate.getForObject(resourceUrl + "/llama?snack=" + snack.getName() , Snack.class);
                snack.preferenceScore(snackPref.getPreferenceScore());
            }

            SnackOrder snackOrder = new SnackOrder();
            log.info(snackOrder.createOrder(100, snackList).toString());
        };
    }

}
