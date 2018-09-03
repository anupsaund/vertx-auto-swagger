package io.vertx.VertxAutoSwagger.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import generator.Required;

public class Product {

    @Required
    @JsonProperty(value = "_id", required = true)
    public String _id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("image_url")
    public String image_url;

    @JsonProperty("from_date")
    public String from_date;

    @JsonProperty("to_date")
    public String to_date;

    @JsonProperty("price")
    public String price;

    @JsonProperty("enabled")
    public Boolean enabled;

}
