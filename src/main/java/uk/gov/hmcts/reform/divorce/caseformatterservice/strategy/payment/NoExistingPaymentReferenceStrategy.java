package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.List;
import java.util.Objects;

@Component
public class NoExistingPaymentReferenceStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        existingPayments.add(PaymentCollection.builder().value(newPayment).build());

        return existingPayments;
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.nonNull(existingPayments) && existingPayments.stream()
            .noneMatch(payment -> payment.getValue()
                .getPaymentReference()
                .equals(newPayment.getPaymentReference()));
    }

}
