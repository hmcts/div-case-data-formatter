package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoExistingPaymentReferenceStrategyUTest {
    private ExistingPaymentReferenceStrategy existingPaymentReferenceStrategy = new ExistingPaymentReferenceStrategy();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        PaymentCollection newPayment = createPayment("111222333", "success");
        PaymentCollection existingPayment = createPayment("999888777", "success");
        PaymentCollection toBeReplacedPayment = createPayment("111222333", "created");

        List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        List<PaymentCollection> expectedPaymentsList = Arrays.asList(existingPayment, newPayment);
        List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }

    private PaymentCollection createPayment(String reference, String status) {
        Payment payment = new Payment();
        payment.setPaymentReference(reference);
        payment.setPaymentStatus(status);

        return PaymentCollection.builder().value(payment).build();
    }
}
