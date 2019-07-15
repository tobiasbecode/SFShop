package de.tobiasbecode.sfshop.order.web.kafka;

import de.tobiasbecode.sfshop.order.web.event.KafkaMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Kafka Producer Configuration
 *
 * includes ProducerFactory
 *  - receives Bootstrap Configuration via KafkaConfiguration
 *  - JSON Serializer for KafkaMessages
 *
 *  injects Kafka Template as Bean in Spring Context-
 */


@Configuration
public class KafkaProducerConfiguration {

    @Autowired
    private KafkaConfiguration kafkaConfiguration;

    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getKafkaServer());

        JsonSerializer<KafkaMessage> valueSerializer = new JsonSerializer<KafkaMessage>();
        valueSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configProps, Serdes.String().serializer(), valueSerializer);
    }


    @Primary
    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
