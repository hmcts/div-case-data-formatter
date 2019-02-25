package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.DateUtil;

@Component
public class SeparationTwoYearsStrategy implements ReasonForDivorceStrategy {

    private static final String SEPARATION_2_YEARS = "separation-2-years";

    private static final String SEPARATION_STRING = "I have been separated from my %s for 2 years or more from the %s.";

    @Autowired
    public SeparationStrategy separationStrategy;

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettySeparationDate = DateUtil.format(
            divorceSession.getReasonForDivorceSeperationDate(), "dd MMMM yyyy"
        );

        return String.format(SEPARATION_STRING, divorceSession.getDivorceWho(), prettySeparationDate);
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return SEPARATION_2_YEARS.equalsIgnoreCase(reasonForDivorce);
    }

    @Override
    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        separationStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);
    }

    @Override
    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {
        separationStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);
    }
}
