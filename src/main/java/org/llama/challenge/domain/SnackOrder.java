package org.llama.challenge.domain;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.llama.challenge.domain.*;
import org.springframework.context.annotation.Configuration;

@JsonIgnoreProperties(ignoreUnknown = true)
@Configuration
public class SnackOrder {

    private List<SnackOrderItem> snackOrder = new ArrayList<SnackOrderItem>();

    public SnackOrder() {
    }


    public List<SnackOrderItem> createOrder(int W, List<Snack> availableSnacks) {
        ArrayList<Integer> preference = new ArrayList<Integer>();
        ArrayList<Integer> price = new ArrayList<Integer>();

        int i = 0;
        snackOrder.clear();
        for(Snack snack : availableSnacks) {
            price.add(i, snack.getPrice());
            preference.add(i, snack.getPreferenceScore());
            snackOrder.add(new SnackOrderItem().snack(snack));
            i++;
        }

        i = 0;
        int order[] = (unboundedOrder(W, preference, price));
        for (Integer orderAmount: order) {
            if (orderAmount > 0) {
                snackOrder.get(i).amount(orderAmount);
            }
            i++;
        }
        return snackOrder;
    }

    // Returns the maximum preference order with the budget of W capacity
    private static int[] unboundedOrder(int W, ArrayList<Integer> preference, ArrayList<Integer> price) {
        int prefNumber = preference.size();
        int prevOrderPref = 0;
        // maxPref[i] is going to store maximum preference of order with value i.
        int maxPref[] = new int[W + 1];
        int order[][] = new int[W + 1][prefNumber];

        // Fill maxPref[] and order[][] using above recursive formula
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j < prefNumber; j++) {
                if (price.get(j) <= i) {
                    prevOrderPref = i - price.get(j);
                    if (maxPref[i] <= (maxPref[prevOrderPref] + preference.get(j))) {
                        maxPref[i] = (maxPref[prevOrderPref] + preference.get(j));

                        System.arraycopy(order[prevOrderPref], 0, order[i], 0, prefNumber);
                        order[i][j]++;
                    }
                }
            }
        }
        return order[W];
    }

    
}


