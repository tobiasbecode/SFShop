package de.tobiasbecode.spring5shop.products.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.products.data.domain.Products;
import de.tobiasbecode.sfshop.products.data.service.ProductsService;
import de.tobiasbecode.sfshop.products.web.api.model.request.ProductsRequestModel;
import de.tobiasbecode.sfshop.products.web.api.model.response.ProductsResponseModel;
import de.tobiasbecode.sfshop.products.web.controller.ProductsController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductsControllerTest {

    @InjectMocks
    ProductsController productsController;

    @Mock
    Products products;

    @Mock
    ProductsService productsService;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        productsController = new ProductsController(productsService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(productsController)
                .build();
    }

    @Test
    public void getProduct() throws Exception {

        ProductsResponseModel products = new ProductsResponseModel();
        products.setId(1L);

        when(productsService.findById(anyLong())).thenReturn(products);

        ResponseEntity responseEntity = productsController.getProduct("1");

        mockMvc.perform(get("/products/1"))
                .andExpect(status().is(200));

        assertEquals(responseEntity.getBody(), products);
        verify(productsService, times(2)).findById(anyLong());
    }

    @Test
    public void getAllProducts() throws Exception {

        ProductsResponseModel products = new ProductsResponseModel();
        products.setId(1L);

        List<ProductsResponseModel> list = new ArrayList<>();

        list.add(products);

        when(productsService.findAll()).thenReturn(list);

        ResponseEntity<List<ProductsResponseModel>> test = productsController.getAllProducts();

        mockMvc.perform(get("/products/list"))
                .andExpect(status().is(200));

        assertEquals(test.getBody(), list);
        verify(productsService, times(2)).findAll();

    }

    @Test
    public void newProduct() throws Exception {

        ProductsResponseModel products = new ProductsResponseModel();
        products.setId(1L);

        when(productsService.newProducts(anyObject())).thenReturn(products);

        ProductsRequestModel productsRequestModel = new ProductsRequestModel();

        ResponseEntity<ProductsResponseModel> newProducts = productsController.newProduct(productsRequestModel);

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(productsRequestModel);

        mockMvc.perform(post("/products/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().is(200));

        verify(productsService, times(2)).newProducts(anyObject());

    }

    @Test
    public void updateProduct() throws Exception {

        ProductsResponseModel products = new ProductsResponseModel();
        products.setId(1L);

        ProductsRequestModel requestModel = new ProductsRequestModel();
        requestModel.setId(1L);

        when(productsService.updateProducts(anyLong(), any())).thenReturn(products);

        ProductsResponseModel responseModel = productsController.updateProduct("1",requestModel);

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(requestModel);

        mockMvc.perform(put("/products/1")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        assertEquals(responseModel, products);
        verify(productsService, times(2)).updateProducts(anyLong(), any());

    }

    @Test
    public void deleteProduct() {

        ProductsRequestModel requestModel = new ProductsRequestModel();
        requestModel.setId(1L);

        productsController.deleteProduct("1");

        verify(productsService, times(1)).removeProducts(anyLong());
    }
}