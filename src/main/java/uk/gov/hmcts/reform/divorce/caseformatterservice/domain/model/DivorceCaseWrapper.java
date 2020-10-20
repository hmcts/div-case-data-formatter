package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model;

import lombok.Value;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

@Value
public class DivorceCaseWrapper {

    private CoreCaseData caseData;
    private DivorceSession divorceSession;

}
