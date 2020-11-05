package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate;

import lombok.Data;
import uk.gov.hmcts.reform.divorce.model.documentupdate.GeneratedDocumentInfo;

import java.util.List;
import java.util.Map;

@Data
public class DocumentUpdateRequest {
    private List<GeneratedDocumentInfo> documents;
    private Map<String, Object> caseData;
}
