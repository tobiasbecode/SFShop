package de.tobiasbecode.sfshop.products.data.kafka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Class - see also application.yml for reference
 */

@Configuration
public class KafkaConfiguration {

    @Getter
    @Value("${kafka.server}")
    private String kafkaServer;

    @Getter
    @Value("${kafka.group}")
    private String kafkaGroup;
}
