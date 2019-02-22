package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.YesNoAnswer;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.DateUtil;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.translateToStringYesNo;

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

    @Override
    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        coreCaseData.setSeparationLivedApartEntireTime(
            translateToStringYesNo(divorceSession.getLivedApartEntireTime())
        );

        coreCaseData.setSeparationLivedTogetherMoreTimeThanPermitted(
            translateToStringYesNo(divorceSession.getLivedTogetherMoreTimeThanPermitted())
        );

        coreCaseData.setSeparationTimeTogetherPermitted(
            divorceSession.getTimeLivedTogetherPermitted()
        );
    }

    @Override
    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {

        divorceSession.setLivedTogetherMoreTimeThanPermitted(
            translateToYesNoString(coreCaseData.getSeparationLivedTogetherMoreTimeThanPermitted())
        );

        divorceSession.setLivedApartEntireTime(
            translateToYesNoString(coreCaseData.getSeparationLivedApartEntireTime())
        );

        divorceSession.setTimeLivedTogetherPermitted(
            coreCaseData.getSeparationTimeTogetherPermitted()
        );
    }

    private String translateToYesNoString(final String value) {
        if (Strings.isBlank(value)) {
            return null;
        }
        return YesNoAnswer.fromInput(value).getAnswer();
    }
}
