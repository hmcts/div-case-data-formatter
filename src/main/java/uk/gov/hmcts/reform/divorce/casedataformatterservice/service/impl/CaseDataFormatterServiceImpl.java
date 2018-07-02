package uk.gov.hmcts.reform.divorce.casedataformatterservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.domain.model.UserDetails;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.service.CaseDataFormatterService;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.service.IdamUserService;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.util.AuthUtil;

@Service
public class CaseDataFormatterServiceImpl implements CaseDataFormatterService {

    @Value("${ccd.jurisdictionid}")
    String jurisdictionId;

    @Value("${ccd.casetype}")
    String caseType;

    @Value("${ccd.eventid.create}")
    String createEventId;


    @Autowired
    private IdamUserService idamUserService;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    UserDetails getUserDetails(String userToken) {
        return idamUserService.retrieveUserDetails(getBearerUserToken(userToken));
    }

    String getBearerUserToken(String userToken) {
        return AuthUtil.getBearToken(userToken);
    }

    String getServiceAuthToken() {
        return authTokenGenerator.generate();
    }

    @Override
    public CaseDetails format(Object data, String authorisation) {
        UserDetails userDetails = getUserDetails(authorisation);

        return CaseDetails.builder().build();
    }
}
