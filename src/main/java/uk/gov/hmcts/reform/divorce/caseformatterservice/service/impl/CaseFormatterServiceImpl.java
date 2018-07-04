package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.UserDetails;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.IdamUserService;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.AuthUtil;

@Service
public class CaseFormatterServiceImpl implements CaseFormatterService {

    @Autowired
    private IdamUserService idamUserService;

    UserDetails getUserDetails(String userToken) {
        return idamUserService.retrieveUserDetails(getBearerUserToken(userToken));
    }

    String getBearerUserToken(String userToken) {
        return AuthUtil.getBearToken(userToken);
    }

    @Override
    public Object format(Object data, String authorisation) {
        UserDetails userDetails = getUserDetails(authorisation);

        return null;
    }
}
