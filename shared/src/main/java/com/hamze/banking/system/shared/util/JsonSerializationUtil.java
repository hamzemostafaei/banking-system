package com.hamze.banking.system.shared.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonSerializationUtil {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final Map<Class<?>, ObjectWriter> OBJECT_WRITER_MAP;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
        OBJECT_WRITER_MAP = new HashMap<>();
        OBJECT_WRITER_MAP.put(null, OBJECT_MAPPER.writerFor(Object.class));
    }

    public static ObjectWriter getWriter(Class<?> type) {

        ObjectWriter objectWriter = OBJECT_WRITER_MAP.get(type);
        if (objectWriter != null) {
            return objectWriter;
        }

        synchronized (OBJECT_MAPPER) {
            objectWriter = OBJECT_WRITER_MAP.get(type);
            if (objectWriter != null) {
                return objectWriter;
            }

            objectWriter = OBJECT_MAPPER.writerFor(type);
            OBJECT_WRITER_MAP.put(type, objectWriter);
        }

        return objectWriter;
    }
    public static String objectToJsonString(Object object) {
        try {
            Class<?> type = object == null ? Object.class : object.getClass();
            ObjectWriter objectWriter = getWriter(type);

            return objectWriter.writeValueAsString(object);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error occurred while serializing object to JSON", e);
            }

            return null;
        }
    }
}
