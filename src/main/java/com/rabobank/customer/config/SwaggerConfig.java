package com.rabobank.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * Builds the Docket for swagger configurations on this API.
     * @return the prepared {@link Docket}
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rabobank.customer"))
                .paths(PathSelectors.regex(".*"))
                .build().apiInfo(apiMetadata());
    }

    /**
     * Prepares the api - info for swagger ui.
     * @return the {@link ApiInfo} prepared.
     */
    private ApiInfo apiMetadata() {
        return new ApiInfoBuilder().title("Customer statement processor")
                .description("This project processes customer statements and generated reports.")
                .build();
    }
}
