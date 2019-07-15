package de.tobiasbecode.sfshop.order.web.kafka;

import de.tobiasbecode.sfshop.order.web.event.KafkaMessage;
import de.tobiasbecode.sfshop.order.web.event.KafkaMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LogProducer implements KafkaMessageListener {

    @Override
    public void onReceive(KafkaMessage kafkaMessage) {
        log(kafkaMessage);
    }

    private void log(KafkaMessage kafkaMessage) {
        log.info(kafkaMessage.topic);
    }

}

