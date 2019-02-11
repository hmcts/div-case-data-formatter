package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

public class DivorceToCCDFormatErrorHandlingTest extends IntegrationTest {
    @Value("${case.formatter.service.transform.toccdformat.context-path}")
    private String contextPath;

    @Test
    public void givenDataIsNull_whenTransformToCCDFormat_thenReturnBadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST.value(),
            RestUtil.postToRestService(getAPIPath(), getHeaders(getUserToken()), null).getStatusCode());
    }

    @Test
    public void givenUserTokenIsNull_whenTransformToCCDFormat_thenReturnBadRequest() throws Exception {
        assertEquals(HttpStatus.BAD_REQUEST.value(),
            RestUtil.postToRestService(getAPIPath(), getHeaders(),
                ResourceLoader.loadJson("fixtures/divorcetoccdmapping/divorce/addresses.json")).getStatusCode());
    }

    @Override
    String getContextPath() {
        return contextPath;
    }

    @Override
    String getExpectedContextPath() {
        return null;
    }
}
