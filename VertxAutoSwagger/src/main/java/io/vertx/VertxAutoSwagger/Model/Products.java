package io.vertx.VertxAutoSwagger.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Products {
    @JsonProperty("products")
    public Product[] products;
}
