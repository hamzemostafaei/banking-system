package com.hamze.banking.system.web.config;

import com.hamze.banking.system.web.api.validation.interceptors.IEdgeRequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@EnableWebMvc
@RequiredArgsConstructor
@Configuration("MVCConfiguration")
public class MVCConfiguration implements WebMvcConfigurer {

    private final IEdgeRequestInterceptor edgeRequestValidatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(edgeRequestValidatorInterceptor);
    }
}
