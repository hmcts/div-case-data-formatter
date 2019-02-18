package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class StringCollectionDivorceFormatMapper {

    @SuppressWarnings(value = "null")
    public List<String> map(List<CollectionMember> collection) {
        if (collection == null) {
            return null;
        }
        return collection.stream()
            .map(value -> (String) value.getValue())
            .collect(Collectors.toList());
    }
}
