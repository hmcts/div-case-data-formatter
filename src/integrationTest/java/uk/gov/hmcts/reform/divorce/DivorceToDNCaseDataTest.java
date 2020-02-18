package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;

public class DivorceToDNCaseDataTest extends IntegrationTest {
    private static final String PAYLOAD_PATH = "fixtures/divorcetoccdmapping/divorce/dn.json";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/ccd/";
    private static final String DN_APPLICATION_SUBMITTED_DATE = "DNApplicationSubmittedDate";
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    @Value("${case.formatter.service.transform.getdncasedata.context-path}")
    private String contextPath;

    @Test
    public void givenDataIsNull_whenGetAosCaseData_thenReturnBadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST.value(),
            RestUtil.postToRestService(getAPIPath(), getHeaders(), null).getStatusCode());
    }

    @Test
    public void whenGetDnCaseData_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(getAPIPath(),
            getHeaders(),
            ResourceLoader.loadJson(PAYLOAD_PATH));

        final Map<String, Object> expectedOutput = getExpected("dn.json");

        expectedOutput.put(DN_APPLICATION_SUBMITTED_DATE, new LocalDate().toString(SIMPLE_DATE_FORMAT));

        final Map<String, Object> actualOutput = getActual(response.getBody().asString());

        assertThat(actualOutput, samePropertyValuesAs(expectedOutput));
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
