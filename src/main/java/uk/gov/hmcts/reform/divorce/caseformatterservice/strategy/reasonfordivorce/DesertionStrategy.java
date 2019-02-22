package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.YesNoAnswer;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.DateUtil;

import static org.apache.commons.lang3.StringUtils.join;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.translateToStringYesNo;

@Component
public class DesertionStrategy implements ReasonForDivorceStrategy {

    private static final String DESERTION = "desertion";

    private static final String LINE_SEPARATOR = "\n";

    private static final String DESERTION_STRING = "I have been deserted by my %s on the %s.";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettyDesertionDate = DateUtil.format(
            divorceSession.getReasonForDivorceDesertionDate(), "dd MMMM yyyy"
        );

        String derivedStatementOfCase = String.format(DESERTION_STRING, divorceSession.getDivorceWho(),
            prettyDesertionDate);

        return join(derivedStatementOfCase, LINE_SEPARATOR, divorceSession.getReasonForDivorceDesertionDetails());
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return DESERTION.equalsIgnoreCase(reasonForDivorce);
    }

    @Override
    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {

        coreCaseData.setDesertionLivedApartEntireTime(
            translateToStringYesNo(divorceSession.getLivedApartEntireTime())
        );

        coreCaseData.setDesertionLivedTogetherMoreTimeThanPermitted(
            translateToStringYesNo(divorceSession.getLivedTogetherMoreTimeThanPermitted())
        );

        coreCaseData.setDesertionTimeTogetherPermitted(
            divorceSession.getTimeLivedTogetherPermitted()
        );
    }

    @Override
    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {

        divorceSession.setLivedTogetherMoreTimeThanPermitted(
            translateToYesNoString(coreCaseData.getDesertionLivedTogetherMoreTimeThanPermitted())
        );

        divorceSession.setLivedApartEntireTime(
            translateToYesNoString(coreCaseData.getDesertionLivedApartEntireTime())
        );

        divorceSession.setTimeLivedTogetherPermitted(
            coreCaseData.getDesertionTimeTogetherPermitted()
        );
    }

    private String translateToYesNoString(final String value) {
        if (Strings.isBlank(value)) {
            return null;
        }
        return YesNoAnswer.fromInput(value).getAnswer();
    }
}
