package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DnCaseData {

    @JsonProperty("DNApplicationSubmittedDate")
    private String dnApplicationSubmittedDate;

    @JsonProperty("PetitionChangedYesNoDN")
    private String petitionChangedYesNoDN;

    @JsonProperty("PetitionChangedDetailsDN")
    private String petitionChangedDetailsDN;

    @JsonProperty("ConfirmPetitionDN")
    private String confirmPetitionDN;

    @JsonProperty("DivorceCostsOptionDN")
    private DynamicList divorceCostsOptionDN;

    @JsonProperty("CostsDifferentDetails")
    private String costsDifferentDetails;

    @JsonProperty("statementOfTruthDN")
    private String statementOfTruthDN;

    @JsonProperty("AlternativeRespCorrAddress")
    private String alternativeRespCorrAddress;

    @JsonProperty("AdulteryLifeIntolerable")
    private String adulteryLifeIntolerable;

    @JsonProperty("AdulteryDateFoundOut")
    private String adulteryDateFoundOut;

    @JsonProperty("DNApplyForDecreeNisi")
    private String applyForDecreeNisi;

    @JsonProperty("AdulteryLivedApartSinceEventDN")
    private String adulteryLivedApartSinceEventDN;

    @JsonProperty("AdulteryTimeLivedTogetherDetailsDN")
    private String adulteryTimeLivedTogetherDetailsDN;

    @JsonProperty("BehaviourStillHappeningDN")
    private String behaviourStillHappeningDN;

    @JsonProperty("BehaviourMostRecentIncidentDateDN")
    private String behaviourMostRecentIncidentDateDN;

    @JsonProperty("BehaviourLivedApartSinceEventDN")
    private String behaviourLivedApartSinceEventDN;

    @JsonProperty("BehaviourTimeLivedTogetherDetailsDN")
    private String behaviourTimeLivedTogetherDetailsDN;

    @JsonProperty("DesertionLivedApartSinceEventDN")
    private String desertionLivedApartSinceEventDN;

    @JsonProperty("DesertionTimeLivedTogetherDetailsDN")
    private String desertionTimeLivedTogetherDetailsDN;

    @JsonProperty("SeparationLivedApartSinceEventDN")
    private String separationLivedApartSinceEventDN;

    @JsonProperty("SeparationTimeLivedTogetherDetailsDN")
    private String separationTimeLivedTogetherDetailsDN;

    @JsonProperty("DocumentsUploadedDN")
    private List<CollectionMember<Document>> documentsUploadedDN;

    @JsonProperty("DocumentsUploadedQuestionDN")
    private String documentsUploadedQuestionDN;

}
