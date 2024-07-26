package com.hamze.banking.system.shared.util;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unused")
public final class SnowFlakeUniqueIDGenerator {
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    // Custom Epoch (January 26, 2024 Midnight UTC = 2024-07-26T00:00:00Z)
    private static final long DEFAULT_CUSTOM_EPOCH = 1721982600000L;

    private static final ConcurrentMap<Long, SnowFlakeUniqueIDGenerator> GENERATOR_MAP = new ConcurrentHashMap<>();

    private final long nodeId;
    private final long customEpoch;
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    // Create SnowFlakeUniqueIDGenerator with a nodeId and custom epoch
    private SnowFlakeUniqueIDGenerator(long nodeId, long customEpoch) {
        if (nodeId < 0 || nodeId > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, MAX_NODE_ID));
        }
        this.nodeId = nodeId;
        this.customEpoch = customEpoch;
    }

    // Create SnowFlakeUniqueIDGenerator with a nodeId
    private SnowFlakeUniqueIDGenerator(long nodeId) {
        this(nodeId, DEFAULT_CUSTOM_EPOCH);
    }

    // Let SnowFlakeUniqueIDGenerator generate a nodeId
    private SnowFlakeUniqueIDGenerator() {
        this.nodeId = generateNodeId();
        this.customEpoch = DEFAULT_CUSTOM_EPOCH;
    }

    private static SnowFlakeUniqueIDGenerator getInstance(long nodeId) {
        return GENERATOR_MAP.computeIfAbsent(nodeId, SnowFlakeUniqueIDGenerator::new);
    }

    public static long generateNextId(long nodeId) {
        SnowFlakeUniqueIDGenerator generator = getInstance(nodeId);
        return generator.generateId();
    }

    private synchronized long generateId() {
        long currentTimestamp = getCurrentTimestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence Exhausted, wait till next millisecond.
                currentTimestamp = waitForNextMillis(currentTimestamp);
            }
        } else {
            // Reset sequence to start with zero for the next millisecond
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return (currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << SEQUENCE_BITS)
                | sequence;
    }

    // Get current timestamp in milliseconds, adjust for the custom epoch.
    private long getCurrentTimestamp() {
        return Instant.now().toEpochMilli() - customEpoch;
    }

    // Block and wait till next millisecond
    private long waitForNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = getCurrentTimestamp();
        }
        return currentTimestamp;
    }

    private long generateNodeId() {
        long nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (byte macPart : mac) {
                        sb.append(String.format("%02X", macPart));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        return nodeId & MAX_NODE_ID;
    }

    @Override
    public String toString() {
        return String.format(
                "UniqueIdGenerator [EPOCH_BITS=%s, NODE_ID_BITS=%s, SEQUENCE_BITS=%s, customEpoch=%s, NodeId=%s]",
                EPOCH_BITS,
                NODE_ID_BITS,
                SEQUENCE_BITS,
                customEpoch,
                nodeId
        );
    }
}
