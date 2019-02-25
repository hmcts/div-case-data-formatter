package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.toYesNoPascalCase;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.toYesNoUpperCase;

public class SeparationStrategy {
    void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        coreCaseData.setSeparationLivedApartEntireTime(
            toYesNoUpperCase(divorceSession.getLivedApartEntireTime())
        );

        coreCaseData.setSeparationLivedTogetherMoreTimeThanPermitted(
            toYesNoUpperCase(divorceSession.getLivedTogetherMoreTimeThanPermitted())
        );

        coreCaseData.setSeparationTimeTogetherPermitted(
            divorceSession.getTimeLivedTogetherPermitted()
        );
    }

    void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {

        divorceSession.setLivedTogetherMoreTimeThanPermitted(
            toYesNoPascalCase(coreCaseData.getSeparationLivedTogetherMoreTimeThanPermitted())
        );

        divorceSession.setLivedApartEntireTime(
            toYesNoPascalCase(coreCaseData.getSeparationLivedApartEntireTime())
        );

        divorceSession.setTimeLivedTogetherPermitted(
            coreCaseData.getSeparationTimeTogetherPermitted()
        );
    }
}
