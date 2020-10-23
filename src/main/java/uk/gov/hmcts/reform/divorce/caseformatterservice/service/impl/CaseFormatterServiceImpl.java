package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDaCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnClarificationMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DocumentCollectionDocumentRequestMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;
import uk.gov.hmcts.reform.divorce.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnRefusalCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.service.DataTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DocumentType.PETITION;

@Service
@RequiredArgsConstructor
public class CaseFormatterServiceImpl implements CaseFormatterService {

    private static final String D8_DOCUMENTS_GENERATED_CCD_FIELD = "D8DocumentsGenerated";
    private static final String GENERIC_DOCUMENT_TYPE = "other";

    private final ObjectMapper objectMapper;
    private final DataTransformer dataTransformer;
    private final CCDCaseToDivorceMapper ccdCaseToDivorceMapper;
    private final DocumentCollectionDocumentRequestMapper documentCollectionDocumentRequestMapper;
    private final DivorceCaseToAosCaseMapper divorceCaseToAosCaseMapper;
    private final DivorceCaseToDnCaseMapper divorceCaseToDnCaseMapper;
    private final DivorceCaseToDnClarificationMapper divorceCaseToDnClarificationMapper;
    private final DivorceCaseToDaCaseMapper divorceCaseToDaCaseMapper;

    @Override
    public CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation) {
        return dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);
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
                    new TypeReference<>() {
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
                new TypeReference<>() {
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
