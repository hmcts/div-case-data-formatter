package uk.gov.hmcts.reform.divorce.caseformatterservice.service;

public interface CaseFormatterService {
    Object format(Object data, String authorisation);
}
