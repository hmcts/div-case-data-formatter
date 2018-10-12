package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SeparationTwoYearsStrategyUTest {
    private static final String SEPARATION_TWO_YEARS = "separation-2-years";

    private final SeparationTwoYearsStrategy separationTwoYearsStrategy = new SeparationTwoYearsStrategy();

    @Test
    public void testSeparationTwoYearsStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_TWO_YEARS);
        divorceSession.setDivorceWho("husband");
        divorceSession.setReasonForDivorceSeperationDate(
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-01-01T00:00:00.000Z"));

        final String derivedStatementOfCase = separationTwoYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("I have been separated from my husband for 2 years or more from the 01 January 2015."));
    }

    @Test
    public void testSeparationTwoYearsWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_TWO_YEARS);

        final String derivedStatementOfCase = separationTwoYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been separated from my null for 2 years or more from the 01 January 1970."));
    }
}
