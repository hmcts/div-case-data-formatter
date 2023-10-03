package uk.gov.hmcts.reform.divorce.caseformatterservice.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "documentation.swagger.enabled", havingValue = "true")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Fees and Payments Service API")
                .description("Service to interact and consolidate fees and payments api"));
    }
}
