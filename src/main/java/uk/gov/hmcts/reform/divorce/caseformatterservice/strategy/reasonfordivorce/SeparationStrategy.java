package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.toYesNoPascalCase;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.toYesNoUpperCase;

@Component
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
