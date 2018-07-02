package uk.gov.hmcts.reform.divorce.casedataformatterservice.management.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.CaseDataFormatterServiceApplication;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "documentation.swagger.enabled", havingValue = "true")
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                    .basePackage(CaseDataFormatterServiceApplication.class.getPackage().getName()))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Divorce Case Data Formatter Service")
                .build();
    }
}
