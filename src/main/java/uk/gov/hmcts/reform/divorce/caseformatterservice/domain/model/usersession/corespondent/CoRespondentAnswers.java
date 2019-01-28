package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoRespondentAnswers {

    private ContactInfo contactInfo;
    private AOS aos;
    private Answer answer;

    private String confirmReadPetition;
    private String statementOfTruth;
    private String admitAdultery;
    private String defendsDivorce;

    private Costs costs;

}
