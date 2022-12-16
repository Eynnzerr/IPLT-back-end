package com.example.relearnspring.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor()).apply {
            addPathPatterns("/**")
        }

        registry.addInterceptor(TokenInterceptor()).apply {
            excludePathPatterns(
                "/user"
            )
        }
    }
}