package uk.gov.hmcts.reform.divorce;

import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceContextConfiguration.class})
public abstract class IntegrationTest {
    @Value("${case.formatter.service.base.uri}")
    String serverUrl;

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration;

    IntegrationTest () {
        this.springMethodIntegration = new SpringIntegrationMethodRule();
    }

}
