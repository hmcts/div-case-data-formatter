package uk.gov.hmcts.reform.divorce.caseformatterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import uk.gov.hmcts.reform.authorisation.healthcheck.ServiceAuthHealthIndicator;

@EnableFeignClients(basePackages = {"uk.gov.hmcts.reform.divorce"})
@SpringBootApplication(exclude = {ServiceAuthHealthIndicator.class})
public class CaseFormatterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseFormatterServiceApplication.class, args);
    }
}
