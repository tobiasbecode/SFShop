package de.tobiasbecode.sfshop.products.web.converters;

import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductsRequestToProductsResponse implements Converter<ProductsRequestModel, ProductsResponseModel> {

    @Override
    public ProductsResponseModel convert(ProductsRequestModel product) {

        ProductsResponseModel responseModel = new ProductsResponseModel();
        responseModel.setName(product.getName());
        responseModel.setPrice(product.getPrice());
        responseModel.setCategory(product.getCategory());
        responseModel.setDescription(product.getDescription());
        responseModel.setOffer(product.isOffer());
        responseModel.setShippingCosts(product.getShippingCosts());
        responseModel.setInventory(product.getInventory());
        responseModel.setUuid(product.getUuid());
        responseModel.setId(product.getId());
        responseModel.setImagePath(product.getImagePath());

        return responseModel;
    }
}
