package com.example.relearnspring.config

import com.example.relearnspring.interceptor.LogInterceptor
import com.example.relearnspring.interceptor.TokenInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// 1. Add interceptors  2. Support CORS
@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor()).apply {
            addPathPatterns("/**")
        }

        registry.addInterceptor(TokenInterceptor()).apply {
            excludePathPatterns(
                //"/user",
                //"/swagger-ui"
                "/**"
            )
        }
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOriginPatterns("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .maxAge(3600)
    }
}