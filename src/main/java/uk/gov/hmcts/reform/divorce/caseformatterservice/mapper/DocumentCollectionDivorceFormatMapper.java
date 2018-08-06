package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.UploadedFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings("squid:S1610")
public abstract class DocumentCollectionDivorceFormatMapper {
    @Mapping(source = "value.documentFileName", target = "fileName")
    @Mapping(source = "value.documentDateAdded", dateFormat = "yyyy-MM-dd", target = "createdOn")
    @Mapping(source = "value.documentLink.documentUrl", target = "fileUrl")
    public abstract UploadedFile map(CollectionMember<Document> documentCollectionMember);
}
