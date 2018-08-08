package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate;

import lombok.Data;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;

import java.util.List;

@Data
public class DocumentUpdateRequest {
    private List<GeneratedDocumentInfo> documents;
    private CoreCaseData caseData;
}
