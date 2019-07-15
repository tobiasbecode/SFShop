package de.tobiasbecode.sfshop.order.web.kafka;

import de.tobiasbecode.sfshop.order.web.event.KafkaMessage;
import de.tobiasbecode.sfshop.order.web.event.KafkaMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * Kafka Producer recieves KafkaMessage via KafkaMessageListener ("onReceive"-Method) from Spring Context
 * Kafka Producer sends it to Cluster (via "sendKafkaMessage"-Method)
 */

@Slf4j
@Component
public class KafkaProducer implements KafkaMessageListener {

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    public void onReceive(KafkaMessage kafkaMessage) {
        sendKafkaMessage(kafkaMessage);
    }

    private void sendKafkaMessage(KafkaMessage kafkaMessage) {
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage);
        kafkaTemplate.flush();
    }
}

