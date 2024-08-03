package com.hamze.banking.system.web.filter;


import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WEbRequestFilter implements Filter {

    public static final String REQUEST_ID_CONTEXT_KEY = "RQST-ID";
    public static final String REQUEST_DATE_CONTEXT_KEY = "RQST-DATE";
    public static final String NODE_ID_CONTEXT_KEY = "NODE-ID";

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            String requestId = Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId));
            String transactionId = Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId));
            Date requestDate = new Date();
            if (request instanceof HttpServletRequest httpRequest) {
                Object trackingNumber = httpRequest.getAttribute("trackingNumber");
                if (trackingNumber == null) {
                    httpRequest.setAttribute("trackingNumber", requestId);
                }
                httpRequest.setAttribute("registrationDate", requestDate);
                httpRequest.setAttribute("transactionId", transactionId);
            }
            MDC.put(NODE_ID_CONTEXT_KEY, String.valueOf(nodeId));
            MDC.put(REQUEST_ID_CONTEXT_KEY, requestId);
            MDC.put(REQUEST_DATE_CONTEXT_KEY, Long.toString(requestDate.getTime()));

        } catch (Exception ex) {
            if (log.isWarnEnabled()) {
                String message = String.format("Something went wrong when processing request: %s", ex.getMessage());
                log.warn(message, ex);
            }
        }

        chain.doFilter(request, response);
    }
}
