package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model;

import lombok.Value;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

@Value
public class DivorceCaseWrapper {
    private CoreCaseData caseData;
    private DivorceSession divorceSession;
}
