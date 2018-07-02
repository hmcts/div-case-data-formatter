package uk.gov.hmcts.reform.divorce.casedataformatterservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.service.CaseDataFormatterService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaseDataFormatterControllerUTest {
    private static final CaseDetails CASE_DETAILS = CaseDetails.builder().build();
    private static final Object CASE_DATA_CONTENT = new Object();
    private static final String JWT_TOKEN = "token";

    @Mock
    private CaseDataFormatterService caseDataFormatterService;


    @InjectMocks
    private CaseDataFormatterController classUnderTest;

    @Test
    public void whenSubmitCase_thenProceedAsExpected() {
        when(caseDataFormatterService.format(CASE_DATA_CONTENT, JWT_TOKEN)).thenReturn(CASE_DETAILS);

        ResponseEntity<CaseDetails> responseEntity = classUnderTest.formatCaseData(CASE_DATA_CONTENT, JWT_TOKEN);

        assertEquals(responseEntity.getBody(), CASE_DETAILS);
        assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value());

        verify(caseDataFormatterService).format(CASE_DATA_CONTENT, JWT_TOKEN);
    }

}
