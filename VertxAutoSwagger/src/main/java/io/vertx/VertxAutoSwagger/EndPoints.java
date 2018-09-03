package io.vertx.VertxAutoSwagger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.vertx.VertxAutoSwagger.Model.*;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

public class EndPoints {

  @Operation(summary = "Find all products", method = "GET", operationId = "products",
    tags = {
      "Product"
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(
          mediaType = "application/json",
          encoding = @Encoding(contentType = "application/json"),
          schema = @Schema(name = "products", example = "{'products':[" +
            "{" +
            "'_id':'abc'," +
            "'title':'Red Truck'," +
            "'image_url':'https://images.pexels.com/photos/1112597/pexels-photo-1112597.jpeg'," +
            "'from_date':'2018-08-30'," +
            "'to_date':'2019-08-30'," +
            "'price':'125.00'," +
            "'enabled':true" +
            "}," +
            "{" +
            "'_id':'def'," +
            "'title':'Blue Truck'," +
            "'image_url':'https://images.pexels.com/photos/1117485/pexels-photo-1117485.jpeg'," +
            "'from_date':'2018-08-30'," +
            "'to_date':'2019-08-30'," +
            "'price':'250.00'," +
            "'enabled':true" +
            "}" +
            "]}",
            implementation = Products.class)
        )
      ),
      @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    }
  )
  public void fetchAllProducts(RoutingContext context)
  {
    JsonArray prods = new JsonArray();

    prods.add(productOne());
    prods.add(productTwo());

    context.response()
      .setStatusCode(200)
      .end(prods.encodePrettily());

  }

  @Operation(summary = "Find products by ID", method = "GET", operationId = "product/:productId",
    tags = {
      "Product"
    },
    parameters = {
      @Parameter(in = ParameterIn.PATH, name = "productId",
        required = true, description = "The unique ID belonging to the product", schema = @Schema(type = "string"))
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(
          mediaType = "application/json",
          encoding = @Encoding(contentType = "application/json"),
          schema = @Schema(name = "product", example =
            "{" +
              "'_id':'abc'," +
              "'title':'Red Truck'," +
              "'image_url':'https://images.pexels.com/photos/1112597/pexels-photo-1112597.jpeg'," +
              "'from_date':'2018-08-30'," +
              "'to_date':'2019-08-30'," +
              "'price':'125.00'," +
              "'enabled':true" +
              "}",
            implementation = Product.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    }
  )
  public void fetchProduct(RoutingContext context)
  {

    switch (context.pathParam("productId")){
      case"abc":
        context.response()
          .setStatusCode(200)
          .end(productOne().encode());
          break;
      case "def":
        context.response()
          .setStatusCode(200)
          .end(productTwo().encode());
        break;
        default:
          context.response()
            .setStatusCode(404)
            .end();

    }

  }

  @Operation(summary = "Add a new Product", method = "POST", operationId = "product",
    description = "Add a new Product",
    tags = {
      "Product"
    },
    requestBody = @RequestBody(
      description = "JSON object of product",
      content = @Content(
        mediaType = "application/json",
        encoding = @Encoding(contentType = "application/json"),
        schema = @Schema(name = "product", example = "{" +
          "'title':'Red Truck'," +
          "'image_url':'https://images.pexels.com/photos/1112597/pexels-photo-1112597.jpeg'," +
          "'from_date':'2018-08-30'," +
          "'to_date':'2019-08-30'," +
          "'price':'125.00'," +
          "'enabled':true" +
          "},", implementation = Product.class)
      ),
      required = true
    ),
    responses = {
      @ApiResponse(responseCode = "400", description = "Bad Request."),
      @ApiResponse(responseCode = "200", description = "Product Created."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    }
  )
  public void addProduct(RoutingContext context)
  {
    final JsonObject product = context.getBodyAsJson();

    ObjectMapper pojoMapper = new ObjectMapper();
    pojoMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    Object sanitised = new Object();

    try {
      // We check to make sure that the payload matches our schema definition
      sanitised = pojoMapper.readValue(product.encode(),Product.class);

      // add code would go here

      context.response()
        .setStatusCode(200).end();


    } catch (IOException e) {
      // The exception will usually be where an item in the payload does not match the Product schema.
      context.response()
        .setStatusCode(400)
        .end(new JsonObject().put("error", e.getMessage()).encode());
    }

  }

  @Operation(summary = "Update an existing product", method = "PUT", operationId = "product",
    description = "Update and existing product",
    tags = {
      "Product"
    },
    requestBody = @RequestBody(
      description = "JSON object of product",
      content = @Content(
        mediaType = "application/json",
        encoding = @Encoding(contentType = "application/json"),
        schema = @Schema(name = "product", example = "{" +
          "'_id':'abc'," +
          "'title':'Red Truck'," +
          "'image_url':'https://images.pexels.com/photos/1112597/pexels-photo-1112597.jpeg'," +
          "'from_date':'2018-08-30'," +
          "'to_date':'2019-08-30'," +
          "'price':'125.00'," +
          "'enabled':true" +
          "},", implementation = Product.class)
      ),
      required = true
    ),
    responses = {
      @ApiResponse(responseCode = "400", description = "Bad Request."),
      @ApiResponse(responseCode = "404", description = "Product not found."),
      @ApiResponse(responseCode = "200", description = "Product Updated."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    }
  )
  public void putProduct(RoutingContext context)
  {
    final JsonObject product = context.getBodyAsJson();
    String productId = product.getString("_id");

    if(productId == null){
      context.response()
        .setStatusCode(400)
        .end();
    }

    if(!productId.equals("abc") && !productId.equals("def")) {
      context.response()
        .setStatusCode(404)
        .end();
    }else {

      ObjectMapper pojoMapper = new ObjectMapper();
      pojoMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
      Object sanitised = new Object();

      try {
        // We check to make sure that the payload matches our schema definition
        sanitised = pojoMapper.readValue(product.encode(),Product.class);

        // Update code would go here.

        context.response()
          .setStatusCode(200).end();

      } catch (IOException e) {
        // The exception will usually be where an item in the payload does not match the Product schema.
        context.response()
          .setStatusCode(400)
          .end(new JsonObject().put("error", e.getMessage()).encode());
      }

    }

  }

  @Operation(summary = "Delete product by ID ", method = "DELETE", operationId = "product/:deleteProductId",
    tags = {
      "Product"
    },
    parameters = {
      @Parameter(in = ParameterIn.PATH, name = "deleteProductId",
        required = true, description = "The ID for the Product", schema = @Schema(type = "string"))
    },
    responses = {
      @ApiResponse(responseCode = "404", description = "Not found."),
      @ApiResponse(responseCode = "200", description = "Product deleted."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    }
  )
  public void deleteProduct(RoutingContext context)
  {
    String productId = context.pathParam("deleteProductId");

    if(!productId.equals("abc") && !productId.equals("def")) {
      context.response()
        .setStatusCode(404)
        .end();
    }else {
      // Delete code would go here
      context.response()
        .setStatusCode(200)
        .end();
    }
  }

  private JsonObject productOne(){
    return new JsonObject()
      .put("_id", "abc")
      .put("title", "Red Truck")
      .put("image_url", "https://images.pexels.com/photos/1112597/pexels-photo-1112597.jpeg")
      .put("from_date", "2018-08-30")
      .put("to_date", "2019-08-30")
      .put("price", "125.00")
      .put("enabled", true);
  }

  private JsonObject productTwo() {
   return new JsonObject()
     .put("_id", "def")
     .put("title", "Blue Truck")
     .put("image_url", "https://images.pexels.com/photos/1117485/pexels-photo-1117485.jpeg")
     .put("from_date", "2018-08-30")
     .put("to_date", "2019-08-30")
     .put("price", "250.00")
     .put("enabled", false);
  }
}
