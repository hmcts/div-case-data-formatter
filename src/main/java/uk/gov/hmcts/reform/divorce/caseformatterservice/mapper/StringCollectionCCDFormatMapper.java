package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StringCollectionCCDFormatMapper {

    public List<CollectionMember> map(List<String> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return collection.stream()
            .map((value) -> {
                final CollectionMember<String> entry = new CollectionMember<>();
                entry.setValue(value);
                return entry;
            })
            .collect(Collectors.toList());
    }
}
