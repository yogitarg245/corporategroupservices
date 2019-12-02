package com.user.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;
import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        Contact contact = new Contact("Yogita Tripathi", "","yogitarg245@gmail.com");
        return new ApiInfo("Yogi Shukla",
                "Swagger OAPI Documents",
                "1.0",
                "Term of Services : Java",
                contact, "Apache Licence Version 2",
                "https://www.apache.org/licences/LICENSES-2.0",
                new ArrayList<>());
    }
}
