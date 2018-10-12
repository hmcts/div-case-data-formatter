package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SeparationFiveYearsStrategyUTest {
    private static final String SEPARATION_FIVE_YEARS = "separation-5-years";

    private final SeparationFiveYearsStrategy separationFiveYearsStrategy = new SeparationFiveYearsStrategy();

    @Test
    public void testSeparationFiveYearsStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_FIVE_YEARS);
        divorceSession.setDivorceWho("wife");
        divorceSession.setReasonForDivorceSeperationDate(
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-01-01T00:00:00.000Z"));

        final String derivedStatementOfCase = separationFiveYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("I have been separated from my wife for 5 years or more from the 01 January 2015."));
    }

    @Test
    public void testSeparationFiveYearsWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_FIVE_YEARS);

        final String derivedStatementOfCase = separationFiveYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been separated from my null for 5 years or more from the 01 January 1970."));
    }
}
