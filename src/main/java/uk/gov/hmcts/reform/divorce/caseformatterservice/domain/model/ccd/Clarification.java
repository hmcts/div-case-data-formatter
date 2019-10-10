package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Clarification {

    @JsonProperty("ClarificationId")
    private String clarificationId;

    @JsonProperty("ClarificationValue")
    private String clarificationValue;
}
