package uk.gov.hmcts.reform.divorce.casedataformatterservice.management.monitoring.health;

import org.springframework.http.HttpEntity;

public interface HttpEntityFactory {

    HttpEntity<Object> createRequestEntityForHealthCheck();
}
