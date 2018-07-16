package uk.gov.hmcts.reform.divorce.caseformatterservice.service;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

public interface CaseFormatterService {

    CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation);

    DivorceSession transformToDivorceSession(CoreCaseData coreCaseData);
}
