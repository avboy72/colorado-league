package com.thescottasylum.nica;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger{
    @Bean
    public Docket halloweenApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("halloween-api")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/v1.*"))
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                .enableUrlTemplating(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Halloween PoC API")
                .description("This is a RESTful service that is implemented in Spring-boot exercising the technologies we are using for V4AF.")
                .termsOfServiceUrl("http://www.vertafore.com/About-Us/Newsroom")
                .contact(new Contact("Sean Scott","https://www.flickr.com/photos/thescottasylum","sean.scott@vertafore.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1")
                .build();
    }
}