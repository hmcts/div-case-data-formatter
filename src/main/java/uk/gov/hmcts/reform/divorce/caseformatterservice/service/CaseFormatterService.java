package uk.gov.hmcts.reform.divorce.caseformatterservice.service;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.List;

public interface CaseFormatterService {

    CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation);

    DivorceSession transformToDivorceSession(CoreCaseData coreCaseData);

    CoreCaseData addDocuments(CoreCaseData coreCaseData, List<GeneratedDocumentInfo> generatedDocumentInfos);

    AosCaseData getAosCaseData(DivorceSession divorceSession);

    DnCaseData getDnCaseData(DivorceSession divorceSession);
}
