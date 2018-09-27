package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSummary {

    @JsonProperty("PaymentReference")
    private String paymentReference;

    @JsonProperty("PaymentTotal")
    private String paymentTotal;

    @JsonProperty("Fees")
    private List<FeeItem> fees;
}
