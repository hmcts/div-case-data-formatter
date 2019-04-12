package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.UploadedFile;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings("squid:S1610")
public abstract class DocumentCollectionDivorceFormatMapper {
    @Mapping(source = "value.documentLink.documentFilename", target = "fileName")
    @Mapping(source = "value.documentDateAdded", dateFormat = SIMPLE_DATE_FORMAT, target = "createdOn")
    @Mapping(source = "value.documentLink.documentUrl", target = "fileUrl")
    @Mapping(source = "id", target = "id")
    public abstract UploadedFile map(CollectionMember<Document> documentCollectionMember);
}
