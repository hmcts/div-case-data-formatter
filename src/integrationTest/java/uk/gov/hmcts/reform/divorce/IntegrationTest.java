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
    private String userToken;
    private static final String emailAddress = "caseformatterservicetest@notifications.service.gov.uk";
    private static final String password = "passowrd";

    @Value("${case.formatter.service.base.uri}")
    String serverUrl;

    @Autowired
    private IdamUtils idamTestSupportUtil;

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration;

    IntegrationTest () {
        this.springMethodIntegration = new SpringIntegrationMethodRule();
    }

    synchronized String getUserToken() {
        if (userToken == null) {
            idamTestSupportUtil.createUserInIdam(emailAddress, password);
            userToken = idamTestSupportUtil.generateUserTokenWithNoRoles(emailAddress, password);
        }

        return userToken;
    }
}
