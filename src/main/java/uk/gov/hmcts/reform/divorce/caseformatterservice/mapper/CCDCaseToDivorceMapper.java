package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.YesNoAnswer;

import java.util.Arrays;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = DocumentCollectionDivorceFormatMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CCDCaseToDivorceMapper {

    private static final String LINE_BREAK = "\n";

    @Mapping(source = "d8HelpWithFeesReferenceNumber", target = "helpWithFeesReferenceNumber")
    @Mapping(source = "d8DivorceWho", target = "divorceWho")
    @Mapping(source = "d8MarriageDate", dateFormat = "yyyy-MM-dd", target = "marriageDate")
    @Mapping(source = "d8ReasonForDivorceDesertionDay", target = "reasonForDivorceDesertionDay")
    @Mapping(source = "d8ReasonForDivorceDesertionMonth", target = "reasonForDivorceDesertionMonth")
    @Mapping(source = "d8ReasonForDivorceDesertionYear", target = "reasonForDivorceDesertionYear")
    @Mapping(source = "d8ReasonForDivorceDesertionDate", dateFormat = "yyyy-MM-dd",
        target = "reasonForDivorceDesertionDate")
    @Mapping(source = "d8CountryName", target = "countryName")
    @Mapping(source = "d8MarriagePlaceOfMarriage", target = "placeOfMarriage")
    @Mapping(source = "d8PetitionerContactDetailsConfidential", target = "petitionerContactDetailsConfidential")
    @Mapping(source = "d8PetitionerHomeAddress.postCode", target = "petitionerHomeAddress.postcode")
    @Mapping(source = "d8PetitionerCorrespondenceAddress.postCode", target = "petitionerCorrespondenceAddress.postcode")
    @Mapping(source = "d8RespondentHomeAddress.postCode", target = "respondentHomeAddress.postcode")
    @Mapping(source = "d8RespondentCorrespondenceAddress.postCode", target = "respondentCorrespondenceAddress.postcode")
    @Mapping(source = "d8PetitionerFirstName", target = "petitionerFirstName")
    @Mapping(source = "d8PetitionerLastName", target = "petitionerLastName")
    @Mapping(source = "d8RespondentFirstName", target = "respondentFirstName")
    @Mapping(source = "d8RespondentLastName", target = "respondentLastName")
    @Mapping(source = "d8PetitionerNameChangedHowOtherDetails", target = "petitionerNameChangedHowOtherDetails")
    @Mapping(source = "d8PetitionerEmail", target = "petitionerEmail")
    @Mapping(source = "d8PetitionerPhoneNumber", target = "petitionerPhoneNumber")
    @Mapping(source = "d8LivingArrangementsLiveTogether", target = "livingArrangementsLiveTogether")
    @Mapping(source = "d8ReasonForDivorce", target = "reasonForDivorce")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyFName", target = "reasonForDivorceAdultery3rdPartyFirstName")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyLName", target = "reasonForDivorceAdultery3rdPartyLastName")
    @Mapping(source = "d8ReasonForDivorceAdulteryDetails", target = "reasonForDivorceAdulteryDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhenDetails", target = "reasonForDivorceAdulteryWhenDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhereDetails", target = "reasonForDivorceAdulteryWhereDetails")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdAddress", target = "reasonForDivorceAdultery3rdAddress")
    @Mapping(source = "d8LegalProceedingsDetails", target = "legalProceedingsDetails")
    @Mapping(source = "d8ResidualJurisdictionEligible", target = "residualJurisdictionEligible")
    @Mapping(source = "d8ReasonForDivorceDesertionDetails", target = "reasonForDivorceDesertionDetails")
    @Mapping(source = "d8JurisdictionConnection", target = "jurisdictionConnection")
    @Mapping(source = "d8FinancialOrderFor", target = "financialOrderFor")
    @Mapping(source = "d8PetitionerNameChangedHow", target = "petitionerNameChangedHow")
    @Mapping(source = "d8LegalProceedingsRelated", target = "legalProceedingsRelated")
    @Mapping(source = "d8DivorceClaimFrom", target = "claimsCostsFrom")
    @Mapping(source = "d8MarriagePetitionerName", target = "marriagePetitionerName")
    @Mapping(source = "d8MarriageRespondentName", target = "marriageRespondentName")
    @Mapping(source = "d8ReasonForDivorceSeperationDay", target = "reasonForDivorceSeperationDay")
    @Mapping(source = "d8ReasonForDivorceSeperationMonth", target = "reasonForDivorceSeperationMonth")
    @Mapping(source = "d8ReasonForDivorceSeperationYear", target = "reasonForDivorceSeperationYear")
    @Mapping(source = "d8ReasonForDivorceSeperationDate", dateFormat = "yyyy-MM-dd",
        target = "reasonForDivorceSeperationDate")
    @Mapping(source = "d8RespondentCorrespondenceUseHomeAddress", target = "respondentCorrespondenceUseHomeAddress")
    @Mapping(source = "d8Connections", target = "connections")
    @Mapping(source = "d8ConnectionSummary", target = "connectionSummary")
    @Mapping(source = "d8DivorceUnit", target = "courts")
    @Mapping(source = "d8DocumentsUploaded", target = "marriageCertificateFiles")
    @Mapping(source = "d8Documents", target = "d8Documents")
    public abstract DivorceSession courtCaseDataToDivorceCaseData(CoreCaseData coreCaseData);

    private String translateToYesNoString(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return YesNoAnswer.fromInput(value).getAnswer();
    }

    private String translateToBooleanString(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return String.valueOf("YES".equalsIgnoreCase(value));
    }

    @AfterMapping
    protected void mapReasonForDivorceBehaviourDetails(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceBehaviourDetails(
            StringUtils.isBlank(caseData.getD8ReasonForDivorceBehaviourDetails())
                ? null : Arrays.asList(caseData.getD8ReasonForDivorceBehaviourDetails().split(LINE_BREAK)));
    }

    @AfterMapping
    protected void mapScreenHasMarriageBroken(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasMarriageBroken(translateToYesNoString(caseData.getD8ScreenHasMarriageBroken()));
    }

    @AfterMapping
    protected void mapScreenHasRespondentAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasRespondentAddress(translateToYesNoString(
            caseData.getD8ScreenHasRespondentAddress()));
    }

    @AfterMapping
    protected void mapScreenHasMarriageCert(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasMarriageCert(translateToYesNoString(caseData.getD8ScreenHasMarriageCert()));
    }

    @AfterMapping
    protected void mapScreenHasPrinter(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasPrinter(translateToYesNoString(caseData.getD8ScreenHasPrinter()));
    }

    @AfterMapping
    protected void mapMarriageIsSameSexCouple(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageIsSameSexCouple(translateToYesNoString(caseData.getD8MarriageIsSameSexCouple()));
    }

    @AfterMapping
    protected void mapMarriedInUk(CoreCaseData caseData,
                                  @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriedInUk(translateToYesNoString(caseData.getD8MarriedInUk()));
    }

    @AfterMapping
    protected void mapCertificateInEnglish(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setCertificateInEnglish(translateToYesNoString(caseData.getD8CertificateInEnglish()));
    }

    @AfterMapping
    protected void mapCertifiedTranslation(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setCertifiedTranslation(translateToYesNoString(caseData.getD8CertifiedTranslation()));
    }

    @AfterMapping
    protected void mapPetitionerNameDifferentToMarriageCert(CoreCaseData caseData,
                                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setPetitionerNameDifferentToMarriageCertificate(
            translateToYesNoString(caseData.getD8PetitionerNameDifferentToMarriageCert()));
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceUseHomeAddress(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setPetitionerCorrespondenceUseHomeAddress(
            translateToYesNoString(caseData.getD8PetitionerCorrespondenceUseHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentNameAsOnMarriageCertificate(CoreCaseData caseData,
                                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentNameAsOnMarriageCertificate(
            translateToYesNoString(caseData.getD8RespondentNameAsOnMarriageCertificate()));
    }

    @AfterMapping
    protected void mapRespondentCorrespondenceSendToSol(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentCorrespondenceSendToSolicitor(
            translateToYesNoString(caseData.getD8RespondentCorrespondenceSendToSol()));
    }

    @AfterMapping
    protected void mapRespondentKnowsHomeAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentKnowsHomeAddress(
            translateToYesNoString(caseData.getD8RespondentKnowsHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentLivesAtLastAddress(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentLivesAtLastAddress(
            translateToYesNoString(caseData.getD8RespondentLivesAtLastAddress()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLastLivedTogether(CoreCaseData caseData,
                                                          @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLivingArrangementsLastLivedTogether(
            translateToYesNoString(caseData.getD8LivingArrangementsLastLivedTogether()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLiveTogether(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLivingArrangementsLiveTogether(
            translateToYesNoString(caseData.getD8LivingArrangementsLiveTogether()));
    }

    @AfterMapping
    protected void mapLegalProceedings(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLegalProceedings(translateToYesNoString(caseData.getD8LegalProceedings()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAgreed(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionAgreed(
            translateToYesNoString(caseData.getD8ReasonForDivorceDesertionAgreed()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhen(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryKnowWhen(
            translateToYesNoString(caseData.getD8ReasonForDivorceAdulteryKnowWhen()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryWishToName(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryWishToName(
            translateToYesNoString(caseData.getD8ReasonForDivorceAdulteryWishToName()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhere(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryKnowWhere(
            translateToYesNoString(caseData.getD8ReasonForDivorceAdulteryKnowWhere()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryIsNamed(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryIsNamed(
            translateToYesNoString(caseData.getD8ReasonForDivorceAdulteryIsNamed()));
    }

    @AfterMapping
    protected void mapFinancialOrder(CoreCaseData caseData,
                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setFinancialOrder(translateToYesNoString(caseData.getD8FinancialOrder()));
    }

    @AfterMapping
    protected void mapHelpWithFeesNeedHelp(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setHelpWithFeesNeedHelp(YesNoAnswer.fromInput(caseData.getD8HelpWithFeesNeedHelp()));
    }

    @AfterMapping
    protected void mapHelpWithFeesAppliedForFees(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setHelpWithFeesAppliedForFees(
            translateToYesNoString(caseData.getD8HelpWithFeesAppliedForFees()));
    }

    @AfterMapping
    protected void mapDivorceCostsClaim(CoreCaseData caseData,
                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClaimsCosts(translateToYesNoString(caseData.getD8DivorceCostsClaim()));
    }

    @AfterMapping
    protected void mapDivorceIsNamed(CoreCaseData caseData,
                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryIsNamed(
            translateToYesNoString(caseData.getD8ReasonForDivorceAdulteryIsNamed()));
    }

    @AfterMapping
    protected void mapJurisdictionConfidentLegal(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionConfidentLegal(
            translateToYesNoString(caseData.getD8JurisdictionConfidentLegal()));
    }

    @AfterMapping
    protected void mapJurisdictionLastTwelveMonths(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionLastTwelveMonths(
            translateToYesNoString(caseData.getD8JurisdictionLastTwelveMonths()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerDomicile(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionPetitionerDomicile(
            translateToYesNoString(caseData.getD8JurisdictionPetitionerDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerResidence(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionPetitionerResidence(
            translateToYesNoString(caseData.getD8JurisdictionPetitionerResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentDomicile(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionRespondentDomicile(
            translateToYesNoString(caseData.getD8JurisdictionRespondentDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentResidence(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionRespondentResidence(
            translateToYesNoString(caseData.getD8JurisdictionRespondentResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionHabituallyResLast6Months(CoreCaseData caseData,
                                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionLastHabitualResident(
            translateToYesNoString(caseData.getD8JurisdictionHabituallyResLast6Months()));
    }

    @AfterMapping
    protected void mapResidualJurisdictionEligible(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setResidualJurisdictionEligible(
            translateToYesNoString(caseData.getD8ResidualJurisdictionEligible()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowAdultery(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowAdultery(
            translateToYesNoString(caseData.getD8ReasonForDivorceShowAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowUnreasonableBehavior(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowUnreasonableBehaviour(
            translateToYesNoString(caseData.getD8ReasonForDivorceShowUnreasonableBehaviour()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowTwoYearsSeparation(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowTwoYearsSeparation(
            translateToYesNoString(caseData.getD8ReasonForDivorceShowTwoYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowDesertion(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowDesertion(
            translateToYesNoString(caseData.getD8ReasonForDivorceShowDesertion()));
    }

    @AfterMapping
    protected void mapReasonForDivorceLimitReasons(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceLimitReasons(
            translateToYesNoString(caseData.getD8ReasonForDivorceLimitReasons()));
    }

    @AfterMapping
    protected void mapReasonForDivorceEnableAdultery(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceEnableAdultery(
            translateToYesNoString(caseData.getD8ReasonForDivorceEnableAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAlright(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionAlright(
            translateToYesNoString(caseData.getD8ReasonForDivorceDesertionAlright()));
    }

    @AfterMapping
    protected void mapClaimsCostsAppliedForFees(CoreCaseData caseData,
                                                @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClaimsCostsAppliedForFees(
            translateToYesNoString(caseData.getD8ClaimsCostsAppliedForFees()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaimingAdultery(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaimingAdultery(
            translateToYesNoString(caseData.getD8ReasonForDivorceClaimingAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationIsSameOrAftr(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationDateIsSameOrAfterLimitDate(
            translateToYesNoString(caseData.getD8ReasonForDivorceSeperationIsSameOrAftr()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationInFuture(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationInFuture(
            translateToYesNoString(caseData.getD8ReasonForDivorceSeperationInFuture()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionBeforeMarriage(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionBeforeMarriage(
            translateToYesNoString(caseData.getD8ReasonForDivorceDesertionBeforeMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionInFuture(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionInFuture(
            translateToYesNoString(caseData.getD8ReasonForDivorceDesertionInFuture()));
    }

    @AfterMapping
    protected void mapMarriageCanDivorce(CoreCaseData caseData,
                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageCanDivorce(
            translateToBooleanString(caseData.getD8MarriageCanDivorce()));
    }

    @AfterMapping
    protected void mapMarriageIsFuture(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageIsFuture(translateToYesNoString(caseData.getD8MarriageIsFuture()));
    }

    @AfterMapping
    protected void mapMarriageMoreThan100(CoreCaseData caseData,
                                          @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageMoreThan100(translateToYesNoString(caseData.getD8MarriageMoreThan100()));
    }

    @AfterMapping
    protected void mapReasonForDivorceHasMarriage(CoreCaseData caseData,
                                                  @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceHasMarriageDate(
            translateToYesNoString(caseData.getD8ReasonForDivorceHasMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowFiveYearsSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowFiveYearsSeparation(
            translateToYesNoString(caseData.getD8ReasonForDivorceShowFiveYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaiming5YearSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaiming5YearSeparation(
            translateToYesNoString(caseData.getD8ReasonForDivorceClaiming5YearSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationBeforeMarriage(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationBeforeMarriage(
            translateToYesNoString(caseData.getD8ReasonForDivorceSeperationBeforeMarriage()));
    }

    @AfterMapping
    protected void mapStatementOfTruth(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setConfirmPrayer(translateToYesNoString(caseData.getD8StatementOfTruth()));
    }

//    @AfterMapping
//    protected void mapPayments(CoreCaseData caseData,
//                               @MappingTarget DivorceSession divorceSession) {
//        if(CollectionUtils.isNotEmpty(caseData.getPayments())){
//
//        }
//
//        if (Objects.nonNull(divorceSession.getPayment())) {
//            if (Objects.nonNull(divorceSession.getPayment().getPaymentDate())) {
//                divorceSession.getPayment().setPaymentDate(LocalDate.parse(
//                    divorceSession.getPayment().getPaymentDate(), DateTimeFormatter.ofPattern("ddMMyyyy"))
//                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            }
//
//            result.setPayments(paymentContext.getListOfPayments(divorceSession));
//        }
//    }
}
