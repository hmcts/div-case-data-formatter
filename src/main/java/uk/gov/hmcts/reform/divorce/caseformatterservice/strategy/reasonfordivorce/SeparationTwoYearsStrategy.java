package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

@Component
public class SeparationTwoYearsStrategy implements ReasonForDivorceStrategy {

    private static final String SEPARATION_2_YEARS = "separation-2-years";

    private static final String SEPARATION_STRING = "I have been separated from my %s for 2 years or more from the %s.";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettySeparationDate = DateFormatUtils.format(divorceSession.getReasonForDivorceSeperationDate(),
            "dd MMMM yyyy");

        return String.format(SEPARATION_STRING, divorceSession.getDivorceWho(), prettySeparationDate);
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return SEPARATION_2_YEARS.equalsIgnoreCase(reasonForDivorce);
    }

}
