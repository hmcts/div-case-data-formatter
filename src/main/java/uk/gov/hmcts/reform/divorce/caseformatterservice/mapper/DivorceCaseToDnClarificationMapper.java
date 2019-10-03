package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = DocumentCollectionCCDFormatMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDnClarificationMapper {

    @Mapping(source = "divorceSession.files", target = "documentsUploadedDnClarification")
    public abstract DnCaseData divorceCaseDataToDnCaseData(DivorceCaseWrapper divorceCaseWrapper);

    @AfterMapping
    protected void mapDnClarificationResponse(DivorceCaseWrapper divorceCaseWrapper,
                                              @MappingTarget DnCaseData result) {

        List<CollectionMember<String>> clarificationReasons =
            Optional.ofNullable(divorceCaseWrapper.getCaseData().getDnClarificationResponse())
                .orElse(new ArrayList<>());

        DivorceSession divorceSession = divorceCaseWrapper.getDivorceSession();

        if (divorceSession.getClarificationResponse() != null) {
            CollectionMember<String> clarificationResponse = new CollectionMember<>();
            clarificationResponse.setValue(divorceSession.getClarificationResponse());

            clarificationReasons.add(clarificationResponse);

            result.setDnClarificationResponse(clarificationReasons);
        }
    }

    @AfterMapping
    protected void mapDocumentsUploadedDnClarification(DivorceCaseWrapper divorceCaseWrapper,
                                                       @MappingTarget DnCaseData result) {

        List<CollectionMember<Document>> clarificationDocuments =
            Optional.ofNullable(divorceCaseWrapper.getCaseData().getDocumentsUploadedDnClarification())
                .orElse(new ArrayList<>());

        // New documents are already added to the result from the @Mapping annotation on the constructor
        // This can then be used in the AfterMapping
        if (result.getDocumentsUploadedDnClarification() != null) {
            clarificationDocuments.addAll(result.getDocumentsUploadedDnClarification());

            result.setDocumentsUploadedDnClarification(clarificationDocuments);
        }
    }
}
