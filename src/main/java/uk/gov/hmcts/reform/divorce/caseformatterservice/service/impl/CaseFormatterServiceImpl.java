package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DocumentCollectionDocumentRequestMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseFormatterServiceImpl implements CaseFormatterService {

    @Autowired
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Autowired
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @Autowired
    private DocumentCollectionDocumentRequestMapper documentCollectionDocumentRequestMapper;

    @Autowired
    private DivorceCaseToAosCaseMapper divorceCaseToAosCaseMapper;

    @Autowired
    private DivorceCaseToDnCaseMapper divorceCaseToDnCaseMapper;

    @Override
    public CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation) {
        return divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession);
    }

    @Override
    public DivorceSession transformToDivorceSession(CoreCaseData coreCaseData) {
        return ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData);
    }

    @Override
    public CoreCaseData addDocuments(CoreCaseData coreCaseData, List<GeneratedDocumentInfo> generatedDocumentInfos) {
        if (coreCaseData != null && CollectionUtils.isNotEmpty(generatedDocumentInfos)) {
            List<CollectionMember<Document>> documents =
                generatedDocumentInfos.stream()
                    .map(documentCollectionDocumentRequestMapper::map)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(coreCaseData.getD8Documents())) {
                documents.addAll(
                    coreCaseData.getD8Documents().stream()
                        .filter(documentCollectionMember ->
                            !generatedDocumentInfos.stream()
                                .map(GeneratedDocumentInfo::getDocumentType)
                                .collect(Collectors.toSet())
                                .contains(documentCollectionMember.getValue().getDocumentType()))
                        .collect(Collectors.toList()));
            }

            coreCaseData.setD8Documents(documents);
        }

        return coreCaseData;
    }

    @Override
    public AosCaseData getAosCaseData(DivorceSession divorceSession) {
        return divorceCaseToAosCaseMapper.divorceCaseDataToAosCaseData(divorceSession);
    }

    @Override
    public DnCaseData getDnCaseData(DivorceSession divorceSession) {
        return divorceCaseToDnCaseMapper.divorceCaseDataToDnCaseData(divorceSession);
    }

}
