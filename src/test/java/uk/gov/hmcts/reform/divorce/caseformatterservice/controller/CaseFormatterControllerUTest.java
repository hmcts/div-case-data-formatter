package uk.gov.hmcts.reform.divorce.caseformatterservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.DocumentUpdateRequest;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaseFormatterControllerUTest {

    @Mock
    private CaseFormatterService caseFormatterService;

    @InjectMocks
    private CaseFormatterController classUnderTest;

    @Test
    public void whenTransformToCCDFormat_thenProceedAsExpected() {
        final DivorceSession divorceSession = new DivorceSession();
        final String userToken = "someToken";

        final CoreCaseData expectedCaseData = new CoreCaseData();

        when(caseFormatterService.transformToCCDFormat(divorceSession, userToken)).thenReturn(expectedCaseData);

        ResponseEntity<CoreCaseData> actualResponse = classUnderTest.transformToCCDFormat(divorceSession, userToken);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedCaseData, actualResponse.getBody());

        verify(caseFormatterService).transformToCCDFormat(divorceSession, userToken);
    }

    @Test
    public void whenTransformToDivorceFormat_thenProceedAsExpected() {
        final CoreCaseData coreCaseData = new CoreCaseData();

        final DivorceSession expectedDivorceSession = new DivorceSession();

        when(caseFormatterService.transformToDivorceSession(coreCaseData)).thenReturn(expectedDivorceSession);

        ResponseEntity<DivorceSession> actualResponse = classUnderTest.transformToDivorceFormat(coreCaseData);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedDivorceSession, actualResponse.getBody());

        verify(caseFormatterService).transformToDivorceSession(coreCaseData);
    }

    @Test
    public void whenAddDocuments_thenProceedAsExpected() {
        final DocumentUpdateRequest documentUpdateRequest = new DocumentUpdateRequest();
        final CoreCaseData coreCaseData = new CoreCaseData();
        final List<GeneratedDocumentInfo> documents = Collections.emptyList();

        documentUpdateRequest.setCaseData(coreCaseData);
        documentUpdateRequest.setDocuments(documents);

        when(caseFormatterService.addDocuments(coreCaseData, documents)).thenReturn(coreCaseData);

        ResponseEntity<CoreCaseData> actualResponse = classUnderTest.addDocuments(documentUpdateRequest);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(coreCaseData, actualResponse.getBody());

        verify(caseFormatterService).addDocuments(coreCaseData, documents);
    }
}
