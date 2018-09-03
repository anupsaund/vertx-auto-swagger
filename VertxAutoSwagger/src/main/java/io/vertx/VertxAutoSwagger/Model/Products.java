package io.vertx.VertxAutoSwagger.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.VertxAutoSwagger.Model.Product;

public class Products {
    @JsonProperty("products")
    public Product[] products;
}
