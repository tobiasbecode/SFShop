package de.tobiasbecode.sfshop.order.web.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * EventListener for incoming Kafka-Messages
 */


public interface KafkaMessageListener {

    @EventListener
    @Async
    void onReceive(KafkaMessage kafkaMessage);

}
