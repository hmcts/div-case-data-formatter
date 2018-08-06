package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.payment;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.payment.PaymentCollection;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class PaymentContext {

    private static final List<PaymentStrategy> paymentStrategies = asList(new ExistingPaymentReferenceStrategy(),
        new NoExistingPaymentReferenceStrategy(), new NoExistingPaymentStrategy());

    public List<PaymentCollection> getListOfPayments(DivorceSession divorceSession) {
        return paymentStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getPayment(), divorceSession.getExistingPayments()))
            .flatMap(paymentStrategy -> paymentStrategy
                .getCurrentPaymentsList(divorceSession.getPayment(), divorceSession.getExistingPayments()).stream())
            .collect(Collectors.toList());
    }
}
