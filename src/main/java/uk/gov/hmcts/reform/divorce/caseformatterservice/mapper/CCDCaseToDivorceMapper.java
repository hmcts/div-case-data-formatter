package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.AddressType;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.YesNoAnswer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = DocumentCollectionDivorceFormatMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings({"PMD.GodClass", "common-java:DuplicatedBlocks"})
public abstract class CCDCaseToDivorceMapper {

    private static final String LINE_BREAK = "\n";
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    @Value("#{${court.details}}")
    private Map<String, Map<String, String>> courtDetails;

    @Mapping(source = "d8HelpWithFeesReferenceNumber", target = "helpWithFeesReferenceNumber")
    @Mapping(source = "d8DivorceWho", target = "divorceWho")
    @Mapping(source = "d8MarriageDate", dateFormat = SIMPLE_DATE_FORMAT, target = "marriageDate")
    @Mapping(source = "d8ReasonForDivorceDesertionDay", target = "reasonForDivorceDesertionDay")
    @Mapping(source = "d8ReasonForDivorceDesertionMonth", target = "reasonForDivorceDesertionMonth")
    @Mapping(source = "d8ReasonForDivorceDesertionYear", target = "reasonForDivorceDesertionYear")
    @Mapping(source = "d8ReasonForDivorceDesertionDate", dateFormat = SIMPLE_DATE_FORMAT,
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
    @Mapping(source = "d8LivingArrangementsLastLivedTogethAddr.postCode",
        target = "livingArrangementsLastLivedTogetherAddress.postcode")
    @Mapping(source = "d8ReasonForDivorce", target = "reasonForDivorce")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyFName", target = "reasonForDivorceAdultery3rdPartyFirstName")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyLName", target = "reasonForDivorceAdultery3rdPartyLastName")
    @Mapping(source = "d8ReasonForDivorceAdulteryDetails", target = "reasonForDivorceAdulteryDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhenDetails", target = "reasonForDivorceAdulteryWhenDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhereDetails", target = "reasonForDivorceAdulteryWhereDetails")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdAddress.postCode",
        target = "reasonForDivorceAdultery3rdAddress.postcode")
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
    @Mapping(source = "d8ReasonForDivorceSeperationDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceSeperationDate")
    @Mapping(source = "d8RespondentCorrespondenceUseHomeAddress", target = "respondentCorrespondenceUseHomeAddress")
    @Mapping(source = "d8Connections", target = "connections")
    @Mapping(source = "d8ConnectionSummary", target = "connectionSummary")
    @Mapping(source = "d8DivorceUnit", target = "courts")
    @Mapping(source = "d8DocumentsUploaded", target = "marriageCertificateFiles")
    @Mapping(source = "d8Documents", target = "d8Documents")
    @Mapping(source = "d8RespondentSolicitorName", target = "respondentSolicitorName")
    @Mapping(source = "d8RespondentSolicitorCompany", target = "respondentSolicitorCompany")
    @Mapping(source = "d8RespondentSolicitorAddress.postCode", target = "respondentSolicitorAddress.postcode")
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
    protected void mapMarriageDate(CoreCaseData caseData,
                                   @MappingTarget DivorceSession divorceSession) {
        LocalDate marriageDate =
            LocalDate.parse(caseData.getD8MarriageDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

        divorceSession.setMarriageDateDay(String.valueOf(marriageDate.getDayOfMonth()));
        divorceSession.setMarriageDateMonth(String.valueOf(marriageDate.getMonthValue()));
        divorceSession.setMarriageDateYear(String.valueOf(marriageDate.getYear()));
    }

    @AfterMapping
    protected void mapCourtDetails(CoreCaseData caseData,
                                   @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(divorceSession.getCourts())) {
            divorceSession.setCourtDetails(courtDetails.get(divorceSession.getCourts()));
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationDate(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        if (caseData.getD8ReasonForDivorceSeperationDate() != null) {
            LocalDate date = LocalDate.parse(
                caseData.getD8ReasonForDivorceSeperationDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

            divorceSession.setReasonForDivorceSeperationDateBeforeMarriageDate(String.valueOf(date.isBefore(
                LocalDate.parse(
                    caseData.getD8MarriageDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
            )));

            divorceSession.setReasonForDivorceSeperationDateInFuture(String.valueOf(date.isAfter(
                LocalDate.parse(
                    caseData.getCreatedDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
            )));

            divorceSession.setReasonForDivorceSeperationDay(String.valueOf(date.getDayOfMonth()));
            divorceSession.setReasonForDivorceSeperationMonth(String.valueOf(date.getMonthValue()));
            divorceSession.setReasonForDivorceSeperationYear(String.valueOf(date.getYear()));
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionDate(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        if (caseData.getD8ReasonForDivorceDesertionDate() != null) {
            LocalDate date = LocalDate.parse(
                caseData.getD8ReasonForDivorceDesertionDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

            divorceSession.setReasonForDivorceDesertionDateInFuture(String.valueOf(date.isAfter(
                LocalDate.parse(
                    caseData.getCreatedDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
            )));

            divorceSession.setReasonForDivorceDesertionDay(String.valueOf(date.getDayOfMonth()));
            divorceSession.setReasonForDivorceDesertionMonth(String.valueOf(date.getMonthValue()));
            divorceSession.setReasonForDivorceDesertionYear(String.valueOf(date.getYear()));
        }
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
            translateToBooleanString(caseData.getD8ReasonForDivorceShowAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowUnreasonableBehavior(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowUnreasonableBehaviour(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowUnreasonableBehaviour()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowTwoYearsSeparation(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowTwoYearsSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowTwoYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowDesertion(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowDesertion(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowDesertion()));
    }

    @AfterMapping
    protected void mapReasonForDivorceLimitReasons(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceLimitReasons(
            translateToBooleanString(caseData.getD8ReasonForDivorceLimitReasons()));
    }

    @AfterMapping
    protected void mapReasonForDivorceEnableAdultery(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceEnableAdultery(
            translateToBooleanString(caseData.getD8ReasonForDivorceEnableAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAlright(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionAlright(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionAlright()));
    }

    @AfterMapping
    protected void mapClaimsCostsAppliedForFees(CoreCaseData caseData,
                                                @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClaimsCostsAppliedForFees(
            translateToBooleanString(caseData.getD8ClaimsCostsAppliedForFees()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaimingAdultery(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaimingAdultery(
            translateToBooleanString(caseData.getD8ReasonForDivorceClaimingAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationIsSameOrAftr(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationDateIsSameOrAfterLimitDate(
            translateToBooleanString(caseData.getD8ReasonForDivorceSeperationIsSameOrAftr()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationInFuture(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationInFuture(
            translateToBooleanString(caseData.getD8ReasonForDivorceSeperationInFuture()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionBeforeMarriage(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionBeforeMarriage(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionBeforeMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionInFuture(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionInFuture(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionInFuture()));
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
    protected void mapPetitionerHomeAddress(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedPetitionerHomeAddress())) {
            divorceSession.getPetitionerHomeAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedPetitionerHomeAddress().split(LINE_BREAK)));

            divorceSession.getPetitionerHomeAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8PetitionerHomeAddress().getPostCode())) {
                divorceSession.getPetitionerHomeAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getPetitionerHomeAddress().setValidPostcode(true);
            } else {
                divorceSession.getPetitionerHomeAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getPetitionerHomeAddress().setAddressAbroad(
                    caseData.getD8DerivedPetitionerHomeAddress());
            }
        }
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceAddress(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedPetitionerCorrespondenceAddress())) {
            divorceSession.getPetitionerCorrespondenceAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedPetitionerCorrespondenceAddress().split(LINE_BREAK)));

            divorceSession.getPetitionerCorrespondenceAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8PetitionerCorrespondenceAddress().getPostCode())) {
                divorceSession.getPetitionerCorrespondenceAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getPetitionerCorrespondenceAddress().setValidPostcode(true);
            } else {
                divorceSession.getPetitionerCorrespondenceAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getPetitionerCorrespondenceAddress().setAddressAbroad(
                    caseData.getD8DerivedPetitionerCorrespondenceAddress());
            }
        }
    }

    @AfterMapping
    protected void mapRespondentHomeAddress(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentHomeAddress())) {
            divorceSession.getRespondentHomeAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentHomeAddress().split(LINE_BREAK)));

            divorceSession.getRespondentHomeAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8RespondentHomeAddress().getPostCode())) {
                divorceSession.getRespondentHomeAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentHomeAddress().setValidPostcode(true);
            } else {
                divorceSession.getRespondentHomeAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getRespondentHomeAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentHomeAddress());
            }
        }
    }

    @AfterMapping
    protected void mapRespondentCorrespondenceAddress(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentCorrespondenceAddr())) {
            divorceSession.getRespondentCorrespondenceAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentCorrespondenceAddr().split(LINE_BREAK)));

            divorceSession.getRespondentCorrespondenceAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8RespondentCorrespondenceAddress().getPostCode())) {
                divorceSession.getRespondentCorrespondenceAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentCorrespondenceAddress().setValidPostcode(true);
            } else {
                divorceSession.getRespondentCorrespondenceAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getRespondentCorrespondenceAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentCorrespondenceAddr());
            }

        }
    }

    @AfterMapping
    protected void mapReasonForDivorceAdultery3rdAddress(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedReasonForDivorceAdultery3rdAddr())) {
            divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedReasonForDivorceAdultery3rdAddr().split(LINE_BREAK)));

            divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8ReasonForDivorceAdultery3rdAddress().getPostCode())) {
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getReasonForDivorceAdultery3rdAddress().setValidPostcode(true);
            } else {
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressAbroad(
                    caseData.getD8DerivedReasonForDivorceAdultery3rdAddr());
            }

        }
    }

    @AfterMapping
    protected void mapReasonForDivorceHasMarriage(CoreCaseData caseData,
                                                  @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceHasMarriageDate(
            translateToBooleanString(caseData.getD8ReasonForDivorceHasMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowFiveYearsSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowFiveYearsSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowFiveYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaiming5YearSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaiming5YearSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceClaiming5YearSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationBeforeMarriage(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationBeforeMarriage(
            translateToYesNoString(caseData.getD8ReasonForDivorceSeperationBeforeMarriage()));
    }

    @AfterMapping
    protected void mapDerivedLivingArrangementsLastLivedAddr(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedLivingArrangementsLastLivedAddr())
            && caseData.getD8LivingArrangementsLastLivedTogethAddr() != null) {
            divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedLivingArrangementsLastLivedAddr().split(LINE_BREAK)));

            divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8LivingArrangementsLastLivedTogethAddr().getPostCode())) {
                divorceSession.getLivingArrangementsLastLivedTogetherAddress()
                    .setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setValidPostcode(true);
            } else {
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressType(
                    AddressType.ABROAD.getType());
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressAbroad(
                    caseData.getD8DerivedLivingArrangementsLastLivedAddr());

            }
        }
    }

    @AfterMapping
    protected void mapRespondentSolicitorAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentSolicitorAddr())
            && caseData.getD8RespondentSolicitorAddress() != null) {
            divorceSession.getRespondentSolicitorAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentSolicitorAddr().split(LINE_BREAK)));

            divorceSession.getRespondentSolicitorAddress().setAddressConfirmed(true);

            if (StringUtils.isNotBlank(caseData.getD8RespondentSolicitorAddress().getPostCode())) {
                divorceSession.getRespondentSolicitorAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentSolicitorAddress().setValidPostcode(true);
            } else {
                divorceSession.getRespondentSolicitorAddress().setAddressType(AddressType.ABROAD.getType());
                divorceSession.getRespondentSolicitorAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentSolicitorAddr());
            }
        }
    }

    @AfterMapping
    protected void mapStatementOfTruth(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setConfirmPrayer(translateToYesNoString(caseData.getD8StatementOfTruth()));
    }

    @AfterMapping
    protected void mapPayments(CoreCaseData caseData,
                               @MappingTarget DivorceSession divorceSession) {
        if (CollectionUtils.isNotEmpty(caseData.getPayments())) {
            divorceSession.setPayment(
                caseData.getPayments().remove(caseData.getPayments().size() - 1).getValue());

            divorceSession.getPayment().setPaymentDate(LocalDate.parse(
                    divorceSession.getPayment().getPaymentDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
                    .format(DateTimeFormatter.ofPattern("ddMMyyyy")));

        }

        if (CollectionUtils.isNotEmpty(caseData.getPayments())) {
            divorceSession.setExistingPayments(caseData.getPayments());
        }
    }
}
