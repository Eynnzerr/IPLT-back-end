package com.example.relearnspring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableOpenApi
class SwaggerConfig {

    @Bean
    fun docket(): Docket = Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .enable(true)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.relearnspring.controller"))
        .build()

    private fun apiInfo() = ApiInfo(
        "API document of IPLT System",
        "made by Eynnzerr",
        "v1.0.0",
        "http://eynnzerr.top",
        Contact("Eynnzerr", "http://eynnzerr.top", "eynnzerr@gmail.com"),
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        emptyList()
    )
}