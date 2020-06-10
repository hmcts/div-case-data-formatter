package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnRefusalCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDaCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnClarificationMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DocumentCollectionDocumentRequestMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DocumentType.PETITION;

@Service
public class CaseFormatterServiceImpl implements CaseFormatterService {

    private static final String D8_DOCUMENTS_GENERATED_CCD_FIELD = "D8DocumentsGenerated";
    private static final String GENERIC_DOCUMENT_TYPE = "other";

    @Autowired
    private ObjectMapper objectMapper;

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

    @Autowired
    private DivorceCaseToDnClarificationMapper divorceCaseToDnClarificationMapper;

    @Autowired
    private DivorceCaseToDaCaseMapper divorceCaseToDaCaseMapper;

    @Override
    public CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation) {
        return divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession);
    }

    @Override
    public DivorceSession transformToDivorceSession(CoreCaseData coreCaseData) {
        return ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData);
    }

    @Override
    public Map<String, Object> addDocuments(Map<String, Object> coreCaseData, List<GeneratedDocumentInfo> generatedDocumentInfos) {

        if (coreCaseData == null) {
            throw new IllegalArgumentException("Existing case data must not be null.");
        }

        if (CollectionUtils.isNotEmpty(generatedDocumentInfos)) {
            List<CollectionMember<Document>> resultDocuments = new ArrayList<>();

            List<CollectionMember<Document>> newDocuments =
                generatedDocumentInfos.stream()
                    .map(documentCollectionDocumentRequestMapper::map)
                    .collect(Collectors.toList());

            Set<String> newDocumentsTypes = generatedDocumentInfos.stream()
                .map(GeneratedDocumentInfo::getDocumentType)
                .collect(Collectors.toSet());

            List<CollectionMember<Document>> documentsGenerated =
                objectMapper.convertValue(coreCaseData.get(D8_DOCUMENTS_GENERATED_CCD_FIELD),
                    new TypeReference<List<CollectionMember<Document>>>() {
                    });

            if (CollectionUtils.isNotEmpty(documentsGenerated)) {
                List<CollectionMember<Document>> existingDocuments = documentsGenerated.stream()
                    .filter(documentCollectionMember ->
                        GENERIC_DOCUMENT_TYPE.equals(documentCollectionMember.getValue().getDocumentType())
                            || !newDocumentsTypes.contains(documentCollectionMember.getValue().getDocumentType())
                    )
                    .collect(Collectors.toList());

                resultDocuments.addAll(existingDocuments);
            }

            resultDocuments.addAll(newDocuments);
            coreCaseData.put(D8_DOCUMENTS_GENERATED_CCD_FIELD, resultDocuments);
        }

        return coreCaseData;
    }

    @Override
    public Map<String, Object> removeAllPetitionDocuments(Map<String, Object> coreCaseData) {
        return removeAllDocumentsByType(coreCaseData, PETITION);
    }

    @Override
    public Map<String, Object> removeAllDocumentsByType(Map<String, Object> coreCaseData, String documentType) {
        if (coreCaseData == null) {
            throw new IllegalArgumentException("Existing case data must not be null.");
        }

        List<CollectionMember<Document>> allDocuments =
            objectMapper.convertValue(coreCaseData.get(D8_DOCUMENTS_GENERATED_CCD_FIELD),
                new TypeReference<List<CollectionMember<Document>>>() {
                });

        if (CollectionUtils.isNotEmpty(allDocuments)) {
            allDocuments.removeIf(documents -> isDocumentType(documents, documentType));
            coreCaseData.replace(D8_DOCUMENTS_GENERATED_CCD_FIELD, allDocuments);
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

    @Override
    public DnRefusalCaseData getDnClarificationCaseData(DivorceCaseWrapper divorceCaseWrapper) {
        return divorceCaseToDnClarificationMapper.divorceCaseDataToDnCaseData(divorceCaseWrapper);
    }

    @Override
    public DaCaseData getDaCaseData(DivorceSession divorceSession) {
        return divorceCaseToDaCaseMapper.divorceCaseDataToDaCaseData(divorceSession);
    }


    private boolean isDocumentType(CollectionMember<Document> document, String documentType) {
        return document.getValue().getDocumentType().equalsIgnoreCase(documentType);
    }
}
