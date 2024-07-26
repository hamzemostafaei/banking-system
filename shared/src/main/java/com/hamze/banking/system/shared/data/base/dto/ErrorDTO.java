package com.hamze.banking.system.shared.data.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "errorCode",
        "errorDescription",
        "referenceName",
        "originalValue",
        "extraData",
        "errorInfo"})
public class ErrorDTO implements Serializable {

    private static final String LINE_SEPARATOR;
    private static final ObjectMapper OBJECT_MAPPER;
    private static final ObjectWriter OBJECT_WRITER;

    static {
        LINE_SEPARATOR = System.lineSeparator();

        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        OBJECT_WRITER = OBJECT_MAPPER.writerFor(ErrorDTO.class);
    }

    @JsonProperty("errorCode")
    private ErrorCodeEnum errorCode;

    @JsonProperty("errorDescription")
    private String errorDescription;

    @JsonProperty("referenceName")
    private String referenceName;

    @JsonProperty("originalValue")
    private String originalValue;

    @JsonProperty("extraData")
    private String extraData;

    @JsonProperty("errorInfo")
    private ErrorInfo errorInfo;

    public ErrorDTO() {
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String referenceName) {
        this.errorCode = errorCode;
        this.referenceName = referenceName;
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String referenceName, Object fieldValue) {
        this.errorCode = errorCode;
        this.referenceName = referenceName;

        try {
            this.originalValue = OBJECT_MAPPER.writeValueAsString(fieldValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String errorDescription, String referenceName) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.referenceName = referenceName;
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String errorDescription, String referenceName, String originalValue) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.referenceName = referenceName;
        this.originalValue = originalValue;
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String errorDescription, String referenceName, Object fieldValue) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.referenceName = referenceName;

        try {
            this.originalValue = OBJECT_MAPPER.writeValueAsString(fieldValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String errorDescription, String referenceName, String originalValue, String extraData) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.referenceName = referenceName;
        this.originalValue = originalValue;
        this.extraData = extraData;
    }

    public ErrorDTO(ErrorCodeEnum errorCode, String errorDescription, String referenceName, Object fieldValue, String extraData) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.referenceName = referenceName;

        try {
            this.originalValue = OBJECT_MAPPER.writeValueAsString(fieldValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        this.extraData = extraData;
    }

    public static String objectToString(Object object) {
        return LINE_SEPARATOR + objectToJSON(object);
    }

    private static String objectToJSON(Object object) {
        try {
            return OBJECT_WRITER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "JSON DUMP ERROR " + e.getMessage();
        }
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDTO errorDTO = (ErrorDTO) o;
        return errorCode == errorDTO.errorCode &&
                Objects.equals(errorDescription, errorDTO.errorDescription) &&
                Objects.equals(referenceName, errorDTO.referenceName) &&
                Objects.equals(originalValue, errorDTO.originalValue) &&
                Objects.equals(extraData, errorDTO.extraData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorDescription, referenceName, originalValue, extraData);
    }

    @Override
    public String toString() {
        return objectToString(this);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorInfo {

        @JsonProperty("localDescription")
        private String localDescription;

        @JsonProperty("globalDescription")
        private String globalDescription;

        @JsonProperty("localTitle")
        private String localTitle;

        @JsonProperty("globalTitle")
        private String globalTitle;
    }
}
