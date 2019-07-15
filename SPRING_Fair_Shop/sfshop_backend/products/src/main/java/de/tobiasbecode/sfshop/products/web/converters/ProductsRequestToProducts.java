package de.tobiasbecode.sfshop.products.web.converters;

import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.data.domain.Products;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductsRequestToProducts implements Converter<ProductsRequestModel, Products> {

    @Override
    public Products convert(ProductsRequestModel product) {

        Products productsModel = new Products();
        productsModel.setName(product.getName());
        productsModel.setPrice(product.getPrice());
        productsModel.setCategory(product.getCategory());
        productsModel.setDescription(product.getDescription());
        productsModel.setOffer(product.isOffer());
        productsModel.setShippingCosts(product.getShippingCosts());
        productsModel.setInventory(product.getInventory());
        productsModel.setUuid(product.getUuid());
        productsModel.setId(product.getId());
        productsModel.setImagePath(product.getImagePath());

        return productsModel;
    }
}
