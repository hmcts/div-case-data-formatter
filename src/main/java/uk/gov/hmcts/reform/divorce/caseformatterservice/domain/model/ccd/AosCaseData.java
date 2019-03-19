package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class AosCaseData extends DnCaseData {
    @JsonProperty("RespConfirmReadPetition")
    private String respConfirmReadPetition;

    @JsonProperty("RespJurisdictionAgree")
    private String respJurisdictionAgree;

    @JsonProperty("RespAdmitOrConsentToFact")
    private String respAdmitOrConsentToFact;

    @JsonProperty("RespWillDefendDivorce")
    private String respWillDefendDivorce;

    @JsonProperty("RespJurisdictionDisagreeReason")
    private String respJurisdictionDisagreeReason;

    @JsonProperty("RespJurisdictionRespCountryOfResidence")
    private String respJurisdictionRespCountryOfResidence;

    @JsonProperty("RespLegalProceedingsExist")
    private String respLegalProceedingsExist;

    @JsonProperty("RespLegalProceedingsDescription")
    private String respLegalProceedingsDescription;

    @JsonProperty("RespAgreeToCosts")
    private String respAgreeToCosts;

    @JsonProperty("RespCostsAmount")
    private String respCostsAmount;

    @JsonProperty("RespCostsReason")
    private String respCostsReason;

    @JsonProperty("RespEmailAddress")
    private String respEmailAddress;

    @JsonProperty("RespPhoneNumber")
    private String respPhoneNumber;

    @JsonProperty("RespStatementOfTruth")
    private String respStatementOfTruth;

    @JsonProperty("RespConsentToEmail")
    private String respConsentToEmail;

    @JsonProperty("RespConsiderFinancialSituation")
    private String respConsiderFinancialSituation;

    @JsonProperty("RespHardshipDefenseResponse")
    private String respHardshipDefenseResponse;

    @JsonProperty("RespHardshipDescription")
    private String respHardshipDescription;

    @JsonProperty("RespContactMethodIsDigital")
    private String respContactMethodIsDigital;

    @JsonProperty("PermittedDecreeNisiReason")
    private String permittedDecreeNisiReason;

    @JsonProperty("ReceivedAOSfromRespDate")
    private Date receivedAosFromRespDate;

    @JsonProperty("ReceivedAOSfromResp")
    private String receivedAosFromResp;

    //Co-respondent answers

    @JsonProperty("CoRespConfirmReadPetition")
    private String coRespConfirmReadPetition;

    @JsonProperty("CoRespAdmitAdultery")
    private String coRespAdmitAdultery;

    @JsonProperty("CoRespAgreeToCosts")
    private String coRespAgreeToCosts;

    @JsonProperty("CoRespConsentToEmail")
    private String coRespConsentToEmail;

    @JsonProperty("CoRespContactMethodIsDigital")
    private String coRespContactMethodIsDigital;

    @JsonProperty("CoRespCostsReason")
    private String coRespCostsReason;

    @JsonProperty("CoRespDefendsDivorce")
    private String coRespDefendsDivorce;

    @JsonProperty("CoRespEmailAddress")
    private String coRespEmailAddress;

    @JsonProperty("CoRespPhoneNumber")
    private String coRespPhoneNumber;

    @JsonProperty("CoRespStatementOfTruth")
    private String coRespStatementOfTruth;

    @JsonProperty("ReceivedAnswerFromCoResp")
    private String receivedAnswerFromCoResp;

    @JsonProperty("ReceivedAnswerFromCoRespDate")
    private String receivedAnswerFromCoRespDate;

    @JsonProperty("ReceivedAosFromCoResp")
    private String receivedAosFromCoResp;

    @JsonProperty("CoRespLetterHolderId")
    private String coRespLetterHolderId;

    @JsonProperty("ReceivedAosFromCoRespDate")
    private String receivedAosFromCoRespDate;

    @JsonProperty("DueDateCoResp")
    private String dueDateCoResp;

    @JsonProperty("CoRespLinkedToCaseDate")
    private String coRespLinkedToCaseDate;

    @JsonProperty("CoRespLinkedToCase")
    private String coRespLinkedToCase;

    //End of co-respondent answers

}
