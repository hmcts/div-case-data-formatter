package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExistingPaymentReferenceStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        List<PaymentCollection> updatedPayments = new ArrayList<>();

        existingPayments.stream()
            .forEach(payment -> {
                if (payment.getValue().getPaymentReference().equals(newPayment.getPaymentReference())) {
                    updatedPayments.add(PaymentCollection.builder()
                        .id(payment.getId())
                        .value(newPayment)
                        .build());
                } else {
                    updatedPayments.add(payment);
                }
            });

        return updatedPayments;
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.nonNull(existingPayments) && existingPayments.stream()
            .anyMatch(
                payment -> payment.getValue().getPaymentReference().equals(newPayment.getPaymentReference()));
    }
}
