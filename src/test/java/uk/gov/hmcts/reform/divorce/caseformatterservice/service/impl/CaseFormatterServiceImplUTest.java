package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.UserDetails;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.IdamUserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaseFormatterServiceImplUTest {

    @Mock
    private IdamUserService idamUserService;

    @Mock
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Mock
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @InjectMocks
    private CaseFormatterServiceImpl classUnderTest;

    @Test
    public void whenTransformToCCDFormat_thenProceedAsExpected() {
        final String userToken = "someToken";
        final String bearerUserToken = "Bearer someToken";
        final DivorceSession divorceSession = new DivorceSession();

        final String emailAddress = "someEmailAddress";
        final DivorceSession divorceSessionWithEmailAddress = new DivorceSession();
        divorceSessionWithEmailAddress.setPetitionerEmail(emailAddress);

        final CoreCaseData expectedCaseData = new CoreCaseData();

        when(idamUserService.retrieveUserDetails(bearerUserToken))
            .thenReturn(UserDetails.builder().email(emailAddress).build());
        when(divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSessionWithEmailAddress))
            .thenReturn(expectedCaseData);

        final CoreCaseData actualCaseData = classUnderTest.transformToCCDFormat(divorceSession, userToken);

        assertEquals(expectedCaseData, actualCaseData);

        verify(idamUserService).retrieveUserDetails(bearerUserToken);
        verify(divorceCaseToCCDMapper).divorceCaseDataToCourtCaseData(divorceSessionWithEmailAddress);
    }

    @Test
    public void whenTransformToDivorceSession_thenProceedAsExpected() {
        final CoreCaseData coreCaseData = new CoreCaseData();
        final DivorceSession expectedDivorceSession = new DivorceSession();

        when(ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData)).thenReturn(expectedDivorceSession);

        final DivorceSession actualDivorceSession = classUnderTest.transformToDivorceSession(coreCaseData);

        assertEquals(expectedDivorceSession, actualDivorceSession);

        verify(ccdCaseToDivorceMapper).courtCaseDataToDivorceCaseData(coreCaseData);
    }
}
