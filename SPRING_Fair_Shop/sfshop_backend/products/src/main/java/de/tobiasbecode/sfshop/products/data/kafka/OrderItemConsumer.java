package de.tobiasbecode.sfshop.products.data.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.products.data.domain.Products;
import de.tobiasbecode.sfshop.products.data.repository.ProductsRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * Kafka Consumer subscribes to topic "ordered item" and transform data into Product POJO
 *
 */

@Component
public class OrderItemConsumer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    KafkaConfiguration kafkaConfiguration;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    Products kafkaProduct;

    @Autowired
    Products kafkaProductToChange;

    private String topic = "ordered_item";

    // Consumer Configuration
    private Map<String, Object> consumerProps() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getKafkaServer());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        return properties;
    }

    //KafkaMessageListenenerContainer is set up with ConsumerFactory (with consumerProps)
    private KafkaMessageListenerContainer<String, KafkaMessage> createContainer(ContainerProperties containerProps) {
        Map<String, Object> props = consumerProps();
        DefaultKafkaConsumerFactory<String, KafkaMessage> consumerFactory = new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<KafkaMessage>(KafkaMessage.class));
        KafkaMessageListenerContainer<String, KafkaMessage> container = new KafkaMessageListenerContainer(consumerFactory, containerProps);
        return container;
    }


    // EventListener reacts on incoming Consumer Record with "listen"-Method
    // listen Method starts Container with topic / group
    // AcknowledgingMessageListener reads value of consumerRecord and transform it with object mapper to Product
    // Product is saved into database
    // Message is acknowledged

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        AcknowledgingMessageListener<String, KafkaMessage> acknowledgingMessageListener = (consumerRecord, acknowledgment) -> {
            try {
                KafkaMessage kafkaMessage = consumerRecord.value();

                // Transform kafkaMessage to Product and save it repository
                ObjectMapper mapper = new ObjectMapper();
                Products kafkaProduct = mapper.readValue(kafkaMessage.getPayload().toString(), Products.class);
                System.out.println(kafkaProduct.getName());
                productsRepository.save(kafkaProduct);

                acknowledgment.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        listen(acknowledgingMessageListener);
    }


    public void listen(AcknowledgingMessageListener<String, KafkaMessage> onMessage) {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(kafkaConfiguration.getKafkaGroup());
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        containerProps.setMessageListener(onMessage);

        KafkaMessageListenerContainer<String, KafkaMessage> container = createContainer(containerProps);
        container.setBeanName(this.getClass().getSimpleName());
        container.start();
    }

}


