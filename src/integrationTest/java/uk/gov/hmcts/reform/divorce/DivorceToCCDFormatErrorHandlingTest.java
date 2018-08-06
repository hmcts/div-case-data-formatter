package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

public class DivorceToCCDFormatErrorHandlingTest extends IntegrationTest {
    private static final String INVALID_USER_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwOTg3NjU0M"
        + "yIsInN1YiI6IjEwMCIsImlhdCI6MTUwODk0MDU3MywiZXhwIjoxNTE5MzAzNDI3LCJkYXRhIjoiY2l0aXplbiIsInR5cGUiOiJBQ0NFU1MiL"
        + "CJpZCI6IjEwMCIsImZvcmVuYW1lIjoiSm9obiIsInN1cm5hbWUiOiJEb2UiLCJkZWZhdWx0LXNlcnZpY2UiOiJEaXZvcmNlIiwibG9hIjoxL"
        + "CJkZWZhdWx0LXVybCI6Imh0dHBzOi8vd3d3Lmdvdi51ayIsImdyb3VwIjoiZGl2b3JjZSJ9.lkNr1vpAP5_Gu97TQa0cRtHu8I-QESzu8kMX"
        + "CJOQrVU";
    private static final String  UNAUTHORISED_JWT_EXCEPTION = "status 403 reading "
        + "IdamUserService#retrieveUserDetails(String); content:\n";

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

    @Test
    public void givenInvalidToken_whenTransformToCCDFormat_thenReturn403() throws Exception {
        Response response = RestUtil.postToRestService(getAPIPath(), getHeaders(INVALID_USER_TOKEN),
            ResourceLoader.loadJson("fixtures/divorcetoccdmapping/divorce/addresses.json"));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
        assertEquals(UNAUTHORISED_JWT_EXCEPTION, response.asString());
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
