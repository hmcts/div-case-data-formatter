package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.List;

public interface PaymentStrategy {

    boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments);

    List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                   List<PaymentCollection> existingPayments);
}
