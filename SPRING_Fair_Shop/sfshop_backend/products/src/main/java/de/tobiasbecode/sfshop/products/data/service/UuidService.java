package de.tobiasbecode.sfshop.products.data.service;

import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/**
 * Creates Random UUID for Products
 *
 */


@Service
public class UuidService {

    public byte[] getUuidBytes() {

        UUID id = UUID.randomUUID();
        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(id.getMostSignificantBits())
                .putLong(id.getLeastSignificantBits());

        return uuidBytes;
    }
}