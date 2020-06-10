package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;

/**
 * Deprecated. This logic was moved to COS. Please refrain from using this method.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Deprecated
public abstract class DocumentCollectionDocumentRequestMapper {
    private static final String HAL_BINARY_RESPONSE_CONTEXT_PATH = "/binary";
    private static final String PDF_FILE_EXTENSION = ".pdf";

    @Mapping(source = "url", target = "value.documentLink.documentUrl")
    @Mapping(source = "url", target = "value.documentLink.documentBinaryUrl")
    @Mapping(source = "fileName", target = "value.documentLink.documentFilename")
    @Mapping(source = "fileName", target = "value.documentFileName")
    @Mapping(source = "documentType", target = "value.documentType")
    public abstract CollectionMember<Document> map(GeneratedDocumentInfo generatedDocumentInfo);

    @AfterMapping
    protected void updateFields(GeneratedDocumentInfo generatedDocumentInfo,
                                @MappingTarget CollectionMember<Document> result) {
        Document document = result.getValue();
        document.getDocumentLink().setDocumentFilename(document.getDocumentLink().getDocumentFilename()
            + PDF_FILE_EXTENSION);
        document.getDocumentLink().setDocumentBinaryUrl(document.getDocumentLink().getDocumentUrl()
            + HAL_BINARY_RESPONSE_CONTEXT_PATH);
    }
}
