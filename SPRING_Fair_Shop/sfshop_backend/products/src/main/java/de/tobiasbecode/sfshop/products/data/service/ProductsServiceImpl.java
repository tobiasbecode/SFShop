package de.tobiasbecode.sfshop.products.data.service;

import de.tobiasbecode.sfshop.products.data.domain.Products;
import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;
import de.tobiasbecode.sfshop.products.web.converters.ProductsRequestToProducts;
import de.tobiasbecode.sfshop.products.web.converters.ProductsRequestToProductsResponse;
import de.tobiasbecode.sfshop.products.web.converters.ProductsResponseModelToProducts;
import de.tobiasbecode.sfshop.products.web.converters.ProductsToProductsRepsonseModel;
import de.tobiasbecode.sfshop.products.data.repository.ProductsRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation
 * - implements methods of ProductsService
 * - uses converter to transform ProductResponseModel/RequestModel to POJO & vice versa
 *
 */



@Service
public class ProductsServiceImpl implements ProductsService {

    //fields
    private final ProductsRepository productsRepository;
    private final UuidService uuidService;
    private final ProductsToProductsRepsonseModel productsToProductsRepsonseModel;
    private final ProductsRequestToProductsResponse requestToResponse;
    private final ProductsResponseModelToProducts responseToProducts;
    private final ProductsRequestToProducts requestToProducts;

    //constructor
    public ProductsServiceImpl(ProductsRepository productsRepository, UuidService uuidService, ProductsToProductsRepsonseModel productsToProductsRepsonseModel, ProductsRequestToProductsResponse requestToResponse, ProductsResponseModelToProducts responseToProdukt, ProductsRequestToProducts requestToProducts) {
        this.productsRepository = productsRepository;
        this.uuidService = uuidService;
        this.productsToProductsRepsonseModel = productsToProductsRepsonseModel;
        this.requestToResponse = requestToResponse;
        this.responseToProducts = responseToProdukt;
        this.requestToProducts = requestToProducts;
    }

    @Override
    public ProductsResponseModel findById (Long id) {

        Products productsDatabase = productsRepository.findById(Long.valueOf(id)).get();
        ProductsResponseModel productsResponse = productsToProductsRepsonseModel.convert(productsDatabase);
        return productsResponse;
    }

    @Override
    public List<ProductsResponseModel> findAll() {

        List<ProductsResponseModel> responseModelList = new ArrayList<>();
        for (Products p: productsRepository.findAll()
             ) {
            responseModelList.add(productsToProductsRepsonseModel.convert(p));
        }
        return responseModelList;

        }


    @Override
    public ProductsResponseModel updateProducts(Long id, ProductsRequestModel requestModel) {

        Products productsDatabase = requestToProducts.convert(requestModel);
        productsDatabase.setId(Long.valueOf(id));
        productsRepository.save(productsDatabase);
        ProductsResponseModel productsResponse = productsToProductsRepsonseModel.convert(productsDatabase);

        return productsResponse;
    }


    @Override
    public ProductsResponseModel newProducts(ProductsRequestModel productsRequestModel) {

        ProductsResponseModel productsResponse = requestToResponse.convert(productsRequestModel);
        Products productsData = responseToProducts.convert(productsResponse);
        byte[] uuidBytes = uuidService.getUuidBytes();
        productsData.setUuid(uuidBytes);
        productsRepository.save(productsData);
        return productsResponse;
    }


    @Override
    public void removeProducts(Long id) {
        productsRepository.deleteById(id);

    }

}
