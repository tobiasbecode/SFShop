package de.tobiasbecode.sfshop.order.web.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.order.data.domain.Address;
import de.tobiasbecode.sfshop.order.data.repository.OrderRepository;
import de.tobiasbecode.sfshop.order.web.event.KafkaMessage;
import de.tobiasbecode.sfshop.order.data.beans.ProductBean;
import de.tobiasbecode.sfshop.order.web.kafka.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * OrderController with REST API for Adress Object(s) / Order with ProductBean send to Kafka Cluster
 */



@RestController
public class OrderController {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Adress - REST API

    @GetMapping("/address/list")
    public List<Address> getAllAdresses() {
        return orderRepository.findAll();
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAdressById(@PathVariable(value = "id") Long addressId)
            throws ResourceNotFoundException {
        Address address = orderRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + addressId));
        return ResponseEntity.ok().body(address);
    }

    @PostMapping("/address/new")
    public Address createEmployee(@Valid @RequestBody Address address) {
        return orderRepository.save(address);
    }




    /**
     * Order - REST API
     *
     * 1. Frontend sends PostRequest with product object
     * 2. Product object is mapped
     * 3. Mapped object is introduced into Spring Context via "sendMessage"-Method as "kafkaMessage"
     */

    @Autowired
    public ObjectMapper objectMapper;


    @PostMapping
    @RequestMapping(path = "/orders")
    public ResponseEntity orderProduct(@RequestBody Object object) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        String jsonString = mapper.writeValueAsString(object);

        System.out.println(object);
        List<ProductBean> list = mapper.readValue(jsonString,
                new TypeReference<ArrayList<ProductBean>>() {
                });

        System.out.println(list);

        list.forEach(productBean -> {
            try {
                sendMessage(productBean);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private LogProducer producer;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendMessage(ProductBean productBean) throws JsonProcessingException {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setTopic("ordered_item");
        kafkaMessage.setPayloadId(productBean.getUuid());
        kafkaMessage.setPayload(objectMapper.writeValueAsString(productBean));
        applicationEventPublisher.publishEvent(kafkaMessage);
    }
}

