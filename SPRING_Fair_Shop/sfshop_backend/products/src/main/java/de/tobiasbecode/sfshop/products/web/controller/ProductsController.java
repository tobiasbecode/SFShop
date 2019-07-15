package de.tobiasbecode.sfshop.products.web.controller;

import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;
import de.tobiasbecode.sfshop.products.data.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductsController with REST API for Products
 */

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private  final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    //Get Request for existing Products
    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductsResponseModel> getProduct(@PathVariable String productId) {
        return new ResponseEntity<>(productsService.findById(Long.valueOf(productId)), HttpStatus.OK);
    }

    //Get Request for existing Products
    @GetMapping(path = "/list")
    public ResponseEntity<List<ProductsResponseModel>> getAllProducts() {
        List <ProductsResponseModel> list = productsService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //Post Request for new Products
    @PostMapping(path = "/new")
    public ResponseEntity<ProductsResponseModel> newProduct(@RequestBody ProductsRequestModel productsRequestModel) {
        ProductsResponseModel newProdukt = productsService.newProducts(productsRequestModel);
        return new ResponseEntity<>(newProdukt, HttpStatus.OK);
    }

    //Get Request for update of existing Products
    @PutMapping(path = "/{productId}")
    public ProductsResponseModel updateProduct (@PathVariable String productId, @RequestBody ProductsRequestModel productsRequestModel) {
        ProductsResponseModel updatedProduct = productsService.updateProducts(Long.valueOf(productId), productsRequestModel);
        return updatedProduct;

    }

    //Delete Mapping for existing Products
    @DeleteMapping(path = "/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productsService.removeProducts(Long.valueOf(productId));
        return ResponseEntity.noContent().build();
    }
}
