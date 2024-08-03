package com.hamze.banking.system.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WEbRequestFilter implements Filter {

    public static final String TRACKING_ID_CONTEXT_KEY = "TRACKING-ID";
    public static final String TRANSACTION_ID_CONTEXT_KEY = "TRANSACTION_ID";
    public static final String REQUEST_DATE_CONTEXT_KEY = "RQST-DATE";
    public static final String NODE_ID_CONTEXT_KEY = "NODE-ID";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);

        try {
            String trackingId = UUID.randomUUID().toString();
            String transactionId = Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId));
            Date requestDate = new Date();

            if (request instanceof HttpServletRequest httpRequest) {
                String requestBody = new String(cachedRequest.getInputStream().readAllBytes());

                if (StringUtils.isNoneBlank(requestBody)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> bodyMap = objectMapper.readValue(requestBody, Map.class);
                    Object requestTrackingId = bodyMap.get("trackingId");
                    if (requestTrackingId != null) {
                        trackingId = requestTrackingId.toString();
                    }
                }

                httpRequest.setAttribute("registrationDate", requestDate);
                httpRequest.setAttribute("transactionId", transactionId);
            }

            MDC.put(NODE_ID_CONTEXT_KEY, String.valueOf(nodeId));
            MDC.put(TRACKING_ID_CONTEXT_KEY, trackingId);
            MDC.put(REQUEST_DATE_CONTEXT_KEY, Long.toString(requestDate.getTime()));
            MDC.put(TRANSACTION_ID_CONTEXT_KEY, transactionId);

            chain.doFilter(cachedRequest, response);

        } catch (Exception ex) {
            if (log.isWarnEnabled()) {
                String message = String.format("Something went wrong when processing request: %s", ex.getMessage());
                log.warn(message, ex);
            }
        } finally {
            MDC.clear();
        }
    }
}
