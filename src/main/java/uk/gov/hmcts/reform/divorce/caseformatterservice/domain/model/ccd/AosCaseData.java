package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @JsonProperty("RespDefendsDivorce")
    private String respDefendsDivorce;

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
}
