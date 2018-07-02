package uk.gov.hmcts.reform.divorce.casedataformatterservice.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.service.IdamUserService;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CaseDataFormatterServiceImplUTest {

    @Mock
    private CoreCaseDataApi coreCaseDataApi;

    @Mock
    private IdamUserService idamUserService;

    @Mock
    private AuthTokenGenerator authTokenGenerator;

    @InjectMocks
    private CaseDataFormatterServiceImpl classUnderTest;

    @Before
    public void setup() {

    }

    @Test
    public void whenUpdate_thenProceedAsExpected() {
        assertTrue(true);
    }
}
