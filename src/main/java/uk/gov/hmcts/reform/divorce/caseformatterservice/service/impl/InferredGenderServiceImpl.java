package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.InferredGenderService;

import java.util.Map;

@Component
public class InferredGenderServiceImpl implements InferredGenderService {
    private final Map<String, Gender> genderMap = ImmutableMap.of("husband", Gender.FEMALE, "wife", Gender.MALE);
    private final Map<String, Gender> roleGender = ImmutableMap.of("husband", Gender.MALE, "wife", Gender.FEMALE);

    @Override
    public Gender getRespondentGender(String respondentRole) {
        return roleGender.get(respondentRole.toLowerCase());
    }

    @Override
    public Gender getPetitionerGender(String isSameSexMarriage, String respondentRole) {
        if ("yes".equalsIgnoreCase(isSameSexMarriage)) {
            return roleGender.get(respondentRole.toLowerCase());
        } else if ("no".equalsIgnoreCase(isSameSexMarriage)) {
            return genderMap.get(respondentRole.toLowerCase());
        }
        return null;
    }
}
