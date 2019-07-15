package de.tobiasbecode.sfshop.products.data.kafka;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KafkaMessage {

    public String topic;
    public UUID payloadId;
    public Object payload;


}
