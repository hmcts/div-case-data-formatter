package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.UserDetails;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.IdamUserService;
import uk.gov.hmcts.reform.divorce.caseformatterservice.util.AuthUtil;

@Service
public class CaseFormatterServiceImpl implements CaseFormatterService {

    @Autowired
    private IdamUserService idamUserService;

    @Autowired
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Autowired
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @Override
    public CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation) {
        UserDetails userDetails = idamUserService.retrieveUserDetails(AuthUtil.getBearToken(authorisation));
        divorceSession.setPetitionerEmail(userDetails.getEmail());

        return divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession);
    }

    @Override
    public DivorceSession transformToDivorceSession(CoreCaseData coreCaseData) {
        return ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData);
    }
}
