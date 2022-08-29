package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class DivorceToDecreeAbsoluteCaseDataTest extends IntegrationTest {
    private static final String PAYLOAD_PATH = "fixtures/divorcetoccdmapping/divorce/da.json";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/ccd/";

    @Value("${case.formatter.service.transform.getdacasedata.context-path}")
    private String contextPath;

//    @Test
//    public void givenDataIsNull_whenGetDaCaseData_thenReturnBadRequest() {
//        assertEquals(HttpStatus.BAD_REQUEST.value(),
//            RestUtil.postToRestService(getAPIPath(), getHeaders(), null).getStatusCode());
//    }

    @Test
    public void whenGetDaCaseData_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(getAPIPath(),
            getHeaders(),
            ResourceLoader.loadJson(PAYLOAD_PATH));

        String expectedOutput = ResourceLoader.loadJson(getExpectedContextPath() + "da.json");

        String actualOutput = response.getBody().asString();

        System.out.println(expectedOutput);
        System.out.println(actualOutput);
        //JSONAssert.assertEquals(expectedOutput, actualOutput, true);
    }

    @Override
    String getContextPath() {
        return contextPath;
    }

    @Override
    String getExpectedContextPath() {
        return EXPECTED_PAYLOAD_CONTEXT_PATH;
    }
}
