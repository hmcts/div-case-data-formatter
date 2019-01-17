package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {

    private String received;
    private Date dateReceived;

}
