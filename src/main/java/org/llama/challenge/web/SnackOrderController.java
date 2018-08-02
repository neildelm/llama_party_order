package org.llama.challenge.web;

import org.llama.challenge.domain.Snack;
import org.llama.challenge.domain.SnackOrderItem;
import org.llama.challenge.service.SnackOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SnackOrderController {
        @Autowired
        private SnackOrderService snackOrderService;

        @RequestMapping(value="/snackOrder", method=GET)
        public ResponseEntity<List<SnackOrderItem>> getSnackOrder(@RequestParam(value="totalAmount") Integer totalAmount ){
            List<String> stores = snackOrderService.getAllStores();
            List<Snack> snackList = snackOrderService.getAllSnacks(stores);
            return ResponseEntity.ok(snackOrderService.getSnackOrder(totalAmount));
        }

}
