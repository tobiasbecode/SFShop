package de.tobiasbecode.sfshop.order.data.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product Bean for communcation with microservice "sfshop-products"
 */


@Getter
@Setter
@NoArgsConstructor
public class ProductBean {

    @JsonProperty
    private String name;

    @JsonProperty
    private double price;

    @JsonProperty
    private String category;

    @JsonProperty
    private String description;

    @JsonProperty
    private boolean offer;

    @JsonProperty
    private String shippingCosts;

    @JsonProperty
    private int inventory;

    @JsonProperty
    private byte[] uuid;

    @JsonProperty
    private Long id;

    @JsonProperty
    private String imagePath;


    public ProductBean(String name,
                       double price, String category, String description, boolean offer,
                       String shippingCosts, int inventory, byte[] uuid, Long id, String imagePath, int amount) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.offer = offer;
        this.shippingCosts = shippingCosts;
        this.inventory = inventory;
        this.uuid = uuid;
        this.id = id;
        this.imagePath = imagePath;

    }
}
