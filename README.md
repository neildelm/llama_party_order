**How To Snack Coding Challenge for MSKCC**

**Installation**

Application can be run using gradle either in an IDE or from the command line: `.gradlew bootRun`

Alternatively, a JAR file for deployment can be built using: `./gradlew build`. Then run the JAR using: `java -jar build/libs/llama_party_order-0.1.0.jar`

Application consumes provided REST services on startup and logs snack order as a list of SnackOrderItems (Snack and Amount).

This is NOT finished design, but just what I have finished. Final design involved producing a web service that would return Snack Order for a passed in total amount. 

 
**Technology Used**

Selected REST service using Spring Boot as it allows a stand-alone application to be created, easing deployment of the snack order microservice.


**Design Decisions Made**

**Snack Order Creation**

Decided to use an implementation of the solution to the unbounded knapsack problem utilizing a dynamic programming method. In this example, the 'knapsack' is the order, limited by the budget available to spend. And the maximum value to optimize is the preference of each snack recorded by the llama.

As the stores are big enough to allow the same item to be ordered as many times as possible, each snack can be ordered more than once.

The Dynamic Programming method works with the idea “the optimum value of if this item put in this knapsack is the optimum value of knapsack weight without this item plus value of this item”. 

Implemented main algorithm using arrays and arrayLists as we know the size in advance (for the arrays), and accessing the index of both data structures is O(1).

Lists have been used to preserve  order of data in objects as the position index of the order[] array refers to the postion of the available snack in snackList.

Overall running of algorithm is O(n^2W) where W is the budget as the algorithm runs from empty to budget value, and n is the number of snacks to evaluate for each budget value (order array is also copied). Whilst this is the worst case, the actual running time should be closer to O(n log n) due to smaller number of time a copy to order[] is required. Also, the number of snacks available is not expected to ever get more than 100. Really.

**REST Service Creation**

POJO objects to represent a Snack, a SnackOrder and a SnackOrderItem created in domain.

Decided to initially test the order creation using `CommandLineRunner` in the SnackOrderApplication code as a quick method for getting the required information using the `RestTemplate`, and then testing the SnackOrder creation using the information fetched.
  
With more time I would finish the REST Controller GET method and service to return a JSON list of SnackOrderItems to represent the SnackOrder. And write tests!