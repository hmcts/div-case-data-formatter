package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SeparationFiveYearsStrategyTest {

    @Mock
    private SeparationStrategy separationStrategy;

    @InjectMocks
    private SeparationFiveYearsStrategy separationFiveYearsStrategy;

    @Test
    public void testDeriveStatementOfCaseGeneratesTheSentence() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorceSeperationDate(
            Date.from(LocalDate.parse("2010-02-01").atStartOfDay().toInstant(ZoneOffset.UTC))
        );

        //when
        String result = separationFiveYearsStrategy.deriveStatementOfCase(divorceSession);

        //then
        assertEquals("I have been separated from my null for 5 years or more from the 01 February 2010.", result);
    }

    @Test
    public void testAcceptsMatchesFiiveYearSeparation() {
        assertTrue(separationFiveYearsStrategy.accepts("sEparAtion-5-Years"));
    }

    @Test
    public void testSetLivedApartFieldsFromDivorceSessionCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationFiveYearsStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);
    }

    @Test
    public void testSetLivedApartFieldsFromCoreCaseDataCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationFiveYearsStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);
    }
}
