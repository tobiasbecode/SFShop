package de.tobiasbecode.sfshop.products.web.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsResponseModel {
    private String name;
    private Double price;
    private String category;
    private String description;
    private boolean offer;
    private double shippingCosts;
    private int inventory;
    private byte[] uuid;
    private long id;
    private String imagePath;
}




