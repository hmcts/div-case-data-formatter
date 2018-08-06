package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

public interface ReasonForDivorceStrategy {

    boolean accepts(String reasonForDivorce);

    String deriveStatementOfCase(DivorceSession divorceSession);
}
