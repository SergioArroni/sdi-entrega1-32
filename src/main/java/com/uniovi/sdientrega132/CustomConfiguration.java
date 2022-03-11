package com.uniovi.sdientrega132;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class CustomConfiguration implements WebMvcConfigurer {

    @Value("${spring.data.web.pageable.page-parameter}")
    private int page;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int size;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(PageRequest.of(page, size));
        argumentResolvers.add(resolver);
    }
}
