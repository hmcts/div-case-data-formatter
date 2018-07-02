package uk.gov.hmcts.reform.divorce.casedataformatterservice.service;

import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;

public interface CaseDataFormatterService {
    CaseDetails format(Object data, String authorisation);
}
