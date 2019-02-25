package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import static org.junit.Assert.assertEquals;

public class SeparationStrategyTest {

    private SeparationStrategy separationStrategy;

    @Before
    public void setup() {
        separationStrategy = new SeparationStrategy();
    }

    @Test
    public void testSetLivedApartFieldsFromDivorceSessionSetsFieldsOnCoreCaseDataObject() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        divorceSession.setLivedApartEntireTime("Yes");
        divorceSession.setLivedTogetherMoreTimeThanPermitted("No");
        divorceSession.setTimeLivedTogetherPermitted("1 month");

        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        //then
        assertEquals(coreCaseData.getSeparationLivedApartEntireTime(), "YES");
        assertEquals(coreCaseData.getSeparationLivedTogetherMoreTimeThanPermitted(), "NO");
        assertEquals(coreCaseData.getSeparationTimeTogetherPermitted(), "1 month");
    }

    @Test
    public void testLivedApartFieldsFromCoreCaseDataSetsFieldsoOnDivorceSessionObject() {
        //given
        CoreCaseData coreCaseData = new CoreCaseData();

        coreCaseData.setSeparationLivedApartEntireTime("YES");
        coreCaseData.setSeparationLivedTogetherMoreTimeThanPermitted("NO");
        coreCaseData.setSeparationTimeTogetherPermitted("1 month");

        DivorceSession divorceSession = new DivorceSession();

        //when
        separationStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        //then
        assertEquals(divorceSession.getLivedApartEntireTime(), "Yes");
        assertEquals(divorceSession.getLivedTogetherMoreTimeThanPermitted(), "No");
        assertEquals(divorceSession.getTimeLivedTogetherPermitted(), "1 month");
    }
}
