package de.tobiasbecode.sfshop.products.web.converters;

import de.tobiasbecode.sfshop.products.data.domain.Products;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductsResponseModelToProducts implements Converter<ProductsResponseModel, Products> {

    @Override
    public Products convert(ProductsResponseModel product) {

        Products productsData = new Products();
        productsData.setName(product.getName());
        productsData.setPrice(product.getPrice());
        productsData.setCategory(product.getCategory());
        productsData.setDescription(product.getDescription());
        productsData.setOffer(product.isOffer());
        productsData.setShippingCosts(product.getShippingCosts());
        productsData.setInventory(product.getInventory());
        productsData.setUuid(product.getUuid());
        productsData.setId(product.getId());
        productsData.setImagePath(product.getImagePath());

        return productsData;
    }
}
