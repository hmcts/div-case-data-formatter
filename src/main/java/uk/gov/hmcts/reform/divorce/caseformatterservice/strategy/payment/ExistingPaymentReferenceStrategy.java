package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExistingPaymentReferenceStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        return existingPayments.stream()
            .map(payment -> mapExistingPayment(payment, newPayment))
            .collect(Collectors.toList());
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.nonNull(existingPayments) && existingPayments.stream()
            .anyMatch(
                payment -> payment.getValue().getPaymentReference().equals(newPayment.getPaymentReference()));
    }

    private PaymentCollection mapExistingPayment(PaymentCollection existingPayment, Payment newPayment) {
        if (existingPayment.getValue().getPaymentReference().equals(newPayment.getPaymentReference())) {
            return existingPayment.toBuilder().value(newPayment).build();
        }

        return existingPayment;
    }
}
