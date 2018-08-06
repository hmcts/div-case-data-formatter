package uk.gov.hmcts.reform.divorce.caseformatterservice.service;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Gender;

public interface InferredGenderService {
    Gender getRespondentGender(String respondentRole);

    Gender getPetitionerGender(String isSameSexMarriage, String respondentRole);
}
