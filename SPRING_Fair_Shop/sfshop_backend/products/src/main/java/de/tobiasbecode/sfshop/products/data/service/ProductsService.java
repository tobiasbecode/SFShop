package de.tobiasbecode.sfshop.products.data.service;

import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;

import java.util.List;


public interface ProductsService {

    ProductsResponseModel newProducts(ProductsRequestModel productsRequestModel);
    ProductsResponseModel findById (Long id);
    List<ProductsResponseModel> findAll();
    ProductsResponseModel updateProducts (Long id, ProductsRequestModel requestModel);
    void removeProducts(Long id);

}
