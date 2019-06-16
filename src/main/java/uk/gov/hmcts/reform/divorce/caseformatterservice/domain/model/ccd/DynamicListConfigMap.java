package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamicListConfigMap {

    public static final Map<String, String> DN_COST_OPTION_MAP = ImmutableMap.of(
        "originalAmount", "I still want to claim my costs and will let the court decide how much",
        "differentAmount", "I want to claim a specific amount",
        "endClaim", "I don't want to claim my costs anymore"
    );

}

