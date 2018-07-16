package uk.gov.hmcts.reform.divorce;

import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceContextConfiguration.class})
public abstract class IntegrationTest {
    private static final String USER_NAME = "caseformatterservicetest";
    static final String EMAIL_ADDRESS = USER_NAME + "@test.com";
    private static final String password = "passowrd";

    private String userToken;

    @Value("${case.formatter.service.base.uri}")
    String serverUrl;

    @Autowired
    private IdamUtils idamTestSupportUtil;

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration;

    IntegrationTest() {
        this.springMethodIntegration = new SpringIntegrationMethodRule();
    }

    String getUserToken() {
        synchronized (this) {
            if (userToken == null) {
                idamTestSupportUtil.createUserInIdam(USER_NAME, password);
                userToken = idamTestSupportUtil.generateUserTokenWithNoRoles(USER_NAME, password);
            }

            return userToken;
        }
    }

    Map<String, Object> getHeaders() {
        return getHeaders(null);
    }

    Map<String, Object> getHeaders(String userToken) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (userToken != null) {
            headers.put(HttpHeaders.AUTHORIZATION, userToken);
        }

        return headers;
    }

    String getAPIPath() {
        return serverUrl + getContextPath();
    }

    abstract String getContextPath();
}
