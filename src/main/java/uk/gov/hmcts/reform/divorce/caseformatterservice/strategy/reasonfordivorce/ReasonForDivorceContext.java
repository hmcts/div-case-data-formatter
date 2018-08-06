package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReasonForDivorceContext {

    private static final List<ReasonForDivorceStrategy> reasonForDivorceStrategies =
        Arrays.asList(
            new AdulteryStrategy(),
            new DesertionStrategy(),
            new SeparationFiveYearsStrategy(),
            new SeparationTwoYearsStrategy(),
            new UnreasonableBehaviourStrategy()
        );

    public String deriveStatementOfWork(DivorceSession divorceSession) {
        return reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .map(s -> s.deriveStatementOfCase(divorceSession))
            .collect(Collectors.joining());
    }
}
