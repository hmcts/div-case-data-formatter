package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.UploadedFile;

import java.util.Optional;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings("squid:S1610")
public abstract class DocumentCollectionDivorceFormatMapper {

    @Autowired
    private DocumentUrlRewrite documentUrlRewrite;

    @Mapping(source = "value.documentLink.documentFilename", target = "fileName")
    @Mapping(source = "value.documentDateAdded", dateFormat = SIMPLE_DATE_FORMAT, target = "createdOn")
    @Mapping(source = "value.documentLink.documentUrl", target = "fileUrl")
    public abstract UploadedFile map(CollectionMember<Document> documentCollectionMember);


    @AfterMapping
    protected void mapDocumentIDToDocumentObject(CollectionMember<Document> document,
                                                 @MappingTarget  UploadedFile result ) {

        Optional.of(document)
            .map(documentEntry -> document.getValue())
            .map(Document::getDocumentLink)
            .map(DocumentLink::getDocumentUrl)
            .ifPresent(url -> documentUrlRewrite.getDocumentId(url).ifPresent(result::setId));
    }
}
