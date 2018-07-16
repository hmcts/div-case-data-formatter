package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import java.util.Collections;

@RunWith(SerenityParameterizedRunner.class)
public class DivorceToCCDFormatTest extends IntegrationTest {
    private static final String PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/divorce/";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/ccd/";

    @Value("${case.formatter.service.transform.toccdformat}")
    private String contextPath;

    private final String input;
    private final String expected;

    public DivorceToCCDFormatTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void whenTransformToCCDFormat_thenReturnExpected() throws Exception {
        Response response = RestUtil.postToRestService(getAPIPath(),
            Collections.singletonMap(HttpHeaders.AUTHORIZATION, getUserToken()),
            ResourceLoader.loadJson(PAYLOAD_CONTEXT_PATH + input));

        
    }

    private String getAPIPath(){
        return serverUrl + contextPath;
    }
}
