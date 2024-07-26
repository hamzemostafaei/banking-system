package com.hamze.banking.system.web.interceptors;

import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import com.hamze.banking.system.web.api.validation.interceptors.IEdgeRequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component("EdgeRequestInterceptor")
public class EdgeRequestInterceptor implements IEdgeRequestInterceptor {

    @Value("${com.hamze.banking.system.node-id}")
    private long nodeId;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String trackingNumber = request.getParameter("trackingNumber");
        String transactionId = request.getParameter("transactionId");

        if (StringUtils.isBlank(trackingNumber)) {
            request.setAttribute("trackingNumber", UUID.randomUUID().toString());
        }
        if (StringUtils.isBlank(transactionId)) {
            request.setAttribute("transactionId", Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId)));
        }

        request.setAttribute("registrationDate", new Date());

        return true;
    }

    //TODO
    /*@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String trackingNumber = (String) request.getAttribute("trackingNumber");
        String transactionId = (String) request.getAttribute("transactionId");
        Date registrationDate = (Date) request.getAttribute("registrationDate");

    }*/
}
