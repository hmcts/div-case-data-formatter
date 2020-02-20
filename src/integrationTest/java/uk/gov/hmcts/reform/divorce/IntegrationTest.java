package uk.gov.hmcts.reform.divorce;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import org.assertj.core.util.Strings;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

@Slf4j
@RunWith(SerenityRunner.class)
@ContextConfiguration(classes = {ServiceContextConfiguration.class})
public abstract class IntegrationTest {

    @Value("${case.formatter.service.base.uri}")
    private String serverUrl;

    @Value("${http.proxy:#{null}}")
    protected String httpProxy;

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration;

    IntegrationTest() {
        this.springMethodIntegration = new SpringIntegrationMethodRule();
    }

    @PostConstruct
    public void init() {
        if (!Strings.isNullOrEmpty(httpProxy)) {
            try {
                URL proxy = new URL(httpProxy);
                if (!InetAddress.getByName(proxy.getHost()).isReachable(2000)) {
                    throw new IOException("Proxy host is not reachable");
                }
                System.setProperty("http.proxyHost", proxy.getHost());
                System.setProperty("http.proxyPort", Integer.toString(proxy.getPort()));
                System.setProperty("https.proxyHost", proxy.getHost());
                System.setProperty("https.proxyPort", Integer.toString(proxy.getPort()));
            } catch (IOException e) {
                log.error("Error setting up proxy - are you connected to the VPN?", e);
                throw new RuntimeException(e);
            }
        }
    }

    Map<String, Object> getHeaders() {
        return getHeaders(null);
    }

    Map<String, Object> getHeaders(String userToken) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

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
