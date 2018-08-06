package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DesertionStrategyUTest {
    private static final String DESERTION = "desertion";

    private final DesertionStrategy desertionStrategy = new DesertionStrategy();

    @Test
    public void testDesertionStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(DESERTION);
        divorceSession.setDivorceWho("husband");
        divorceSession
            .setReasonForDivorceDesertionDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse("2015-02-01T00:00:00.000Z"));
        divorceSession.setReasonForDivorceDesertionDetails("He told me that he is going to his mother.");

        final String derivedStatementOfCase = desertionStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been deserted by my husband on the 01 February 2015.\nHe told me that he is going to his mother."));
    }
}
