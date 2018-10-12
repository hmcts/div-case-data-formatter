package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.DateUtil;

@Component
public class SeparationFiveYearsStrategy implements ReasonForDivorceStrategy {

    private static final String SEPARATION_5_YEARS = "separation-5-years";

    private static final String SEPARATION_STRING = "I have been separated from my %s for 5 years or more from the %s.";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettySeparationDate = DateUtil.format(
            divorceSession.getReasonForDivorceSeperationDate(), "dd MMMM yyyy"
        );

        return String.format(SEPARATION_STRING, divorceSession.getDivorceWho(), prettySeparationDate);
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return SEPARATION_5_YEARS.equalsIgnoreCase(reasonForDivorce);
    }

}
