package uk.gov.hmcts.reform.divorce.caseformatterservice.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
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

    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .forEach(s -> s.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData));
    }

    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {
        reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .forEach(s -> s.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession));
    }
}
