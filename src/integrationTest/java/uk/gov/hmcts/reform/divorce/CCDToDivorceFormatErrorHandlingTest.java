package uk.gov.hmcts.reform.divorce;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

public class CCDToDivorceFormatErrorHandlingTest extends IntegrationTest {
    @Value("${case.formatter.service.transform.todivorceformat.context-path}")
    private String contextPath;

    @Test
    public void givenDataIsNull_whenTransformToDivorceFormat_thenReturnBadRequest() {
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),
            RestUtil.postToRestService(getAPIPath(), getHeaders(), null).getStatusCode());
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
