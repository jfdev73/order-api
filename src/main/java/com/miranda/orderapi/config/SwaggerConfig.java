package com.miranda.orderapi.config;




import java.util.Arrays;
import java.util.Collections;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.globalOperationParameters(Arrays.asList(
    		            new ParameterBuilder().name("Authorization")
    		                .description("Header de autenticación")
    		                .modelRef(new ModelRef("string"))
    		                .parameterType("header")
    		                .required(false)
    		                .build()
    		        ))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.miranda.orderapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
	
	
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Order API",
                "La API REST de Ordenes.",
                "v1",
                "Terms of service",
                new Contact("JFdev73", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
    
	/*
	 * @Override protected void addResourceHandlers(ResourceHandlerRegistry
	 * registry) { registry.addResourceHandler("swagger-ui.html")
	 * .addResourceLocations("classpath:/META-INF/resources/");
	 * registry.addResourceHandler("/webjars/**")
	 * .addResourceLocations("classpath:/META-INF/resources/webjars/"); }
	 */
}
