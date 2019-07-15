package de.tobiasbecode.sfshop.order.web.event;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaMessage {

    public String topic;
    public byte[] payloadId;
    public Object payload;


}
