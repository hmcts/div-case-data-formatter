package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class RemoveAllPetitionDocumentsTest extends IntegrationTest {
    private static final String PAYLOAD_PATH = "fixtures/model/ccd/removeAllPetitionDocumentsInput.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/model/ccd/";

    @Value("${case.formatter.service.transform.removeallpetitiondocuments.context-path}")
    private String contextPath;

    //    @Test
    //    public void givenDataIsNull_whenRemoveAllPetitions_thenReturnBadRequest() {
    //        Assert.assertEquals(
    //            HttpStatus.BAD_REQUEST.value(),
    //            RestUtil.postToRestService(getAPIPath(), getHeaders(),null).getStatusCode()
    //        );
    //    }

    @Test
    public void whenRemoveAllPetitions_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(
            getAPIPath(),
            getHeaders(),
            ResourceLoader.loadJson(PAYLOAD_PATH)
        );

        final Map<String, Object> expectedOutput = getExpected("removeAllPetitionDocumentsOutput.json");

        final Map<String, Object> actualOutput = getActual(response.getBody().asString());

        System.out.println(expectedOutput);
        System.out.println(actualOutput);
        //Assert.assertEquals(expectedOutput, actualOutput);
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
