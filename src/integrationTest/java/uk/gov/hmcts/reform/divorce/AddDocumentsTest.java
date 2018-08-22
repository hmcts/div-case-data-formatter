package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class AddDocumentsTest extends IntegrationTest {
    private static final String PAYLOAD_PATH = "fixtures/model/ccd/addDocumentsInput.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/model/ccd/";

    @Value("${case.formatter.service.transform.adddocuments.context-path}")
    private String contextPath;

    @Test
    public void givenDataIsNull_whenAddDocuments_thenReturnBadRequest() {
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),
            RestUtil.postToRestService(getAPIPath(), getHeaders(), null).getStatusCode());
    }

    @Test
    public void whenAddDocuments_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(getAPIPath(),
            getHeaders(),
            ResourceLoader.loadJson(PAYLOAD_PATH));

        final Map<String, Object> expectedOutput = getExpected("addDocumentsOutput.json");
        expectedOutput.put("respondentLetterHolderId", null);

        final Map<String, Object> actualOutput = getActual(response.getBody().asString());

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Override
    String getContextPath() {
        return contextPath;
    }

    @Override
    String getExpectedContextPath() {
        return EXPECTED_PAYLOAD_PATH;
    }
}
