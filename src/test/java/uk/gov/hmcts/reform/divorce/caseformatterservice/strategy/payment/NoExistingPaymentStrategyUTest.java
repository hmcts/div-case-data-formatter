package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoExistingPaymentStrategyUTest {
    private NoExistingPaymentStrategy noExistingPaymentStrategy = new NoExistingPaymentStrategy();

    @Test
    public void testNoExistingPaymentsAddsJustNewPayment() {
        Payment newPayment = new Payment();
        newPayment.setPaymentReference("111222333");

        List<PaymentCollection> existingPaymentsList = null;

        List<PaymentCollection> expectedPaymentsList = Collections.singletonList(PaymentCollection.builder()
            .value(newPayment).build());

        List<PaymentCollection> returnedPaymentsList =
            noExistingPaymentStrategy.getCurrentPaymentsList(newPayment, existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }
}
