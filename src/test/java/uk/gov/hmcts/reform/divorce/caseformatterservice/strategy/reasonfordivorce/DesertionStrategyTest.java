package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import static org.junit.Assert.assertEquals;


public class DesertionStrategyTest {

    private DesertionStrategy desertionStrategy;

    @Before
    public void setup() {
        desertionStrategy = new DesertionStrategy();
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
        desertionStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        //then
        assertEquals(coreCaseData.getDesertionLivedApartEntireTime(), "YES");
        assertEquals(coreCaseData.getDesertionLivedTogetherMoreTimeThanPermitted(), "NO");
        assertEquals(coreCaseData.getDesertionTimeTogetherPermitted(), "1 month");
    }

    @Test
    public void testLivedApartFieldsFromCoreCaseDataSetsFieldsoOnDivorceSessionObject() {
        //given
        CoreCaseData coreCaseData = new CoreCaseData();

        coreCaseData.setDesertionLivedApartEntireTime("YES");
        coreCaseData.setDesertionLivedTogetherMoreTimeThanPermitted("NO");
        coreCaseData.setDesertionTimeTogetherPermitted("1 month");

        DivorceSession divorceSession = new DivorceSession();

        //when
        desertionStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        //then
        assertEquals(divorceSession.getLivedApartEntireTime(), "Yes");
        assertEquals(divorceSession.getLivedTogetherMoreTimeThanPermitted(), "No");
        assertEquals(divorceSession.getTimeLivedTogetherPermitted(), "1 month");
    }
}
