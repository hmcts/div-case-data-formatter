package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.translateToStringYesNo;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.translateToYesNoString;

public class SeparationStrategy {
    void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
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

    void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {

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
}
