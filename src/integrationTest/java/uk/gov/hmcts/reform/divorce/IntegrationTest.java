package uk.gov.hmcts.reform.divorce;

import net.serenitybdd.junit.runners.SerenityRunner;
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
import java.util.UUID;

@RunWith(SerenityRunner.class)
@ContextConfiguration(classes = {ServiceContextConfiguration.class})
public abstract class IntegrationTest {
    private static final String password = "genericPassword1";

    private String userToken;

    @Value("${case.formatter.service.base.uri}")
    private String serverUrl;

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
                String username = "divorce+cfs-test-" + UUID.randomUUID();
                idamTestSupportUtil.createUserInIdam(username, password);
                userToken = idamTestSupportUtil.generateUserTokenWithNoRoles(username, password);
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

    @SuppressWarnings("unchecked")
    Map<String, Object> getExpected(String fileName) throws Exception {
        return (Map<String, Object>)ObjectMapperUtil.jsonStringToObject(
            ResourceLoader.loadJson(getExpectedContextPath() + fileName), Map.class);
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> getActual(String json) throws Exception {
        return (Map<String, Object>)ObjectMapperUtil.jsonStringToObject(json, Map.class);
    }

    String getAPIPath() {
        return serverUrl + getContextPath();
    }

    abstract String getContextPath();

    abstract String getExpectedContextPath();
}
