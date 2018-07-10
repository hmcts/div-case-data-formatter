package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.junit.Assert;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.InferredGenderService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InferredGenderServiceImplUTest {
    private InferredGenderService inferredGenderService = new InferredGenderServiceImpl();

    @Test
    public void shouldReturnMalePetitionerWhenSameSexAndDivorcingHusband() {
        String respondentRole = "husband";

        Assert.assertEquals(Gender.MALE, inferredGenderService.getPetitionerGender("Yes", respondentRole));
    }

    @Test
    public void shouldReturnFemalePetitionerWhenSameSexAndDivorcingWife() {
        String respondentRole = "wife";

        assertEquals(Gender.FEMALE, inferredGenderService.getPetitionerGender("Yes", respondentRole));
    }

    @Test
    public void shouldReturnFemalePetitionerWhenNotSameSexAndDivorcingHusband() {
        String respondentRole = "husband";

        assertEquals(Gender.FEMALE, inferredGenderService.getPetitionerGender("No", respondentRole));
    }

    @Test
    public void shouldReturnMalePetitionerWhenNotSameSexAndDivorcingWife() {
        String respondentRole = "wife";

        assertEquals(Gender.MALE, inferredGenderService.getPetitionerGender("No", respondentRole));
    }

    @Test
    public void shouldReturnMaleRespondentWhenRespondentIsHusband() {
        String respondentRole = "husband";
        assertEquals(Gender.MALE, inferredGenderService.getRespondentGender(respondentRole));
    }

    @Test
    public void shouldReturnFemaleRespondentWhenRespondentIsWife() {
        String respondentRole = "wife";
        assertEquals(Gender.FEMALE, inferredGenderService.getRespondentGender(respondentRole));
    }

    @Test
    public void shouldReturnNullIfRespondentRoleNotMatched() {
        assertNull(inferredGenderService.getPetitionerGender("Yes", "notValid"));
    }

    @Test
    public void shouldReturnNullIfIsSameSexNotMatched() {
        assertNull(inferredGenderService.getPetitionerGender("notValid", "husband"));
    }
}
