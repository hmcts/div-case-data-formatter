package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.*;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterServiceV2;


@Service
public class CaseFormatterServiceImplV2 implements CaseFormatterServiceV2 {

    @Autowired
    private DivorceCaseToCCDMapperV2 divorceCaseToCCDMapper;

    @Override
    public Object transformToCCDFormat(String divorceSession) {
        return divorceCaseToCCDMapper.transformToCCDFormat(divorceSession);
    }

}
