package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressType {
    POST_CODE("postcode"),
    ABROAD("manual");

    private String type;
}
