package uk.gov.hmcts.reform.divorce.caseformatterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"uk.gov.hmcts.reform.divorce"})
public class CaseFormatterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseFormatterServiceApplication.class, args);
    }

}
