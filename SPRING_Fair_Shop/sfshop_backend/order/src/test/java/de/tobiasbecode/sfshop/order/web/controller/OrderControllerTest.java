package de.tobiasbecode.sfshop.order.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.order.data.beans.ProductBean;
import de.tobiasbecode.sfshop.order.data.domain.Address;
import de.tobiasbecode.sfshop.order.data.repository.OrderRepository;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    ProductBean productBean;

    @Mock
    OrderRepository orderRepository;
    MockMvc mockMVC;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(orderRepository);

        mockMVC = MockMvcBuilders
                .standaloneSetup(orderController)
                .build();
    }

    @Test
    public void getAllAddresses() throws Exception {

        Address newAddress = new Address();
        List addressList = new ArrayList<>();
        addressList.add(newAddress);

        when(orderRepository.findAll()).thenReturn(addressList);

        List<Address> testAddressList = orderController.getAllAdresses();

        mockMVC.perform(get("/address/list"))
                .andExpect(status().is(200));

        assertEquals(testAddressList.size(), 1);
        verify(orderRepository, times(2)).findAll();
    }

    @Test
    public void getAdressById() throws Exception {
        Address newAddress = new Address();
        newAddress.setId(1L);

        when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(newAddress));


        mockMVC.perform(get("/address/1"))
                .andExpect(status().is(200));

        ResponseEntity testAdress = orderController.getAdressById(1L);

        assertEquals(newAddress,testAdress.getBody());
        verify(orderRepository, times(2)).findById(anyLong());

    }


    @Test
    public void orderProduct() throws Exception {

        ProductBean productBean = new ProductBean();
        productBean.setName("test");

        List <ProductBean> listTest = new ArrayList<>();

        ObjectMapper om = new ObjectMapper();

        String request = om.writeValueAsString(listTest);
        System.out.println(listTest);

        mockMVC.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNoContent());
    }


}