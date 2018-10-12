package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.Date;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.join;

@Component
public class DesertionStrategy implements ReasonForDivorceStrategy {

    private static final String DESERTION = "desertion";

    private static final String LINE_SEPARATOR = "\n";

    private static final String DESERTION_STRING = "I have been deserted by my %s on the %s.";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettyDesertionDate = DateFormatUtils.format(
            Optional.ofNullable(divorceSession.getReasonForDivorceDesertionDate()).orElse(new Date(0)),
            "dd MMMM yyyy");

        String derivedStatementOfCase = String.format(DESERTION_STRING, divorceSession.getDivorceWho(),
            prettyDesertionDate);

        return join(derivedStatementOfCase, LINE_SEPARATOR, divorceSession.getReasonForDivorceDesertionDetails());
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return DESERTION.equalsIgnoreCase(reasonForDivorce);
    }

}
