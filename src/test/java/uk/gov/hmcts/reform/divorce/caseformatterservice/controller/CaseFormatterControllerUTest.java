package uk.gov.hmcts.reform.divorce.caseformatterservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.DocumentUpdateRequest;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
        final Map<String, Object> coreCaseData = new HashMap<>();
        final List<GeneratedDocumentInfo> documents = Collections.emptyList();

        documentUpdateRequest.setCaseData(coreCaseData);
        documentUpdateRequest.setDocuments(documents);

        when(caseFormatterService.addDocuments(coreCaseData, documents)).thenReturn(coreCaseData);

        ResponseEntity<Map<String, Object>> actualResponse = classUnderTest.addDocuments(documentUpdateRequest);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(coreCaseData, actualResponse.getBody());

        verify(caseFormatterService).addDocuments(coreCaseData, documents);
    }

    @Test
    public void whenGetAosCaseData_thenProceedAsExpected() {
        DivorceSession divorceSession = mock(DivorceSession.class);
        AosCaseData aosCaseData = mock(AosCaseData.class);

        when(caseFormatterService.getAosCaseData(divorceSession)).thenReturn(aosCaseData);

        ResponseEntity<AosCaseData> actual = classUnderTest.getAosCaseData(divorceSession);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(aosCaseData, actual.getBody());

        verify(caseFormatterService).getAosCaseData(divorceSession);
    }

    @Test
    public void whenGetDnCaseData_thenProceedAsExpected() {
        DivorceSession divorceSession = mock(DivorceSession.class);
        DnCaseData dnCaseData = mock(DnCaseData.class);

        when(caseFormatterService.getDnCaseData(divorceSession)).thenReturn(dnCaseData);

        ResponseEntity<DnCaseData> actual = classUnderTest.getDnCaseData(divorceSession);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(dnCaseData, actual.getBody());

        verify(caseFormatterService).getDnCaseData(divorceSession);
    }

    @Test
    public void whenGetDaCaseData_thenProceedAsExpected() {
        DivorceSession divorceSession = mock(DivorceSession.class);
        DaCaseData daCaseData = mock(DaCaseData.class);

        when(caseFormatterService.getDaCaseData(divorceSession)).thenReturn(daCaseData);

        ResponseEntity<DaCaseData> actual = classUnderTest.getDaCaseData(divorceSession);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(daCaseData, actual.getBody());

        verify(caseFormatterService).getDaCaseData(divorceSession);
    }
}
