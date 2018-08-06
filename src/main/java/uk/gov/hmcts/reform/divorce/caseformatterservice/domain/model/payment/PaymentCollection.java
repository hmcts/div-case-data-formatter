package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCollection {
    private String id;
    private Payment value;
}
