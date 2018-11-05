package uk.gov.hmcts.reform.divorce.caseformatterservice.service;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

public interface SeparationDateService {
    void updateSeparationDate(DivorceSession divorceSession);
}
