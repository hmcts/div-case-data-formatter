package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring", uses = DocumentCollectionCCDFormatMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDnCaseMapper {

    public static final String YES = "YES";

    @Mapping(source = "files", target = "documentsUploadedDN")
    @Mapping(source = "changesDetails", target = "petitionChangedDetailsDN")
    @Mapping(source = "claimCosts", target = "divorceCostsOptionDN")
    @Mapping(source = "costsDifferentDetails", target = "costsDifferentDetails")
    @Mapping(source = "statementOfTruth", target = "statementOfTruthDN")
    @Mapping(source = "intolerable", target = "adulteryLifeIntolerable")
    @Mapping(source = "adulteryFirstFoundDate", dateFormat = SIMPLE_DATE_FORMAT, target = "adulteryDateFoundOut")
    @Mapping(source = "livedApartSinceAdultery", target = "adulteryLivedApartSinceEventDN")
    @Mapping(source = "datesLivedTogether", target = "adulteryTimeLivedTogetherDetailsDN")
    @Mapping(source = "behaviourContinuedSinceApplication", target = "behaviourStillHappeningDN")
    @Mapping(source = "lastIncidentDate", dateFormat = SIMPLE_DATE_FORMAT, target = "behaviourMostRecentIncidentDateDN")
    @Mapping(source = "livedApartSinceLastIncidentDate", target = "behaviourLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "behaviourTimeLivedTogetherDetailsDN")
    @Mapping(source = "livedApartSinceDesertion", target = "desertionLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "desertionTimeLivedTogetherDetailsDN")
    @Mapping(source = "livedApartSinceSeparation", target = "separationLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "separationTimeLivedTogetherDetailsDN")
    public abstract DnCaseData divorceCaseDataToDnCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void mapDnApplicationSubmittedDate(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDnApplicationSubmittedDate(LocalDate.now().format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT)));
    }

    @AfterMapping
    protected void mapConfirmPetitionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        if (YES.equalsIgnoreCase(divorceSession.getStatementOfTruthChanges())
            || YES.equalsIgnoreCase(divorceSession.getStatementOfTruthNoChanges())) {
            result.setConfirmPetitionDN(YES);
        }
    }


    @AfterMapping
    protected void mapAgreeToApplyDn(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setApplyForDecreeNisi(translateToStringYesNo(divorceSession.getApplyForDecreeNisi()));
    }

    @AfterMapping
    protected void mapDocumentsUploadedQuestionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setDocumentsUploadedQuestionDN(translateToStringYesNo(divorceSession.getUploadAnyOtherDocuments()));
    }

    @AfterMapping
    protected void mapPetitionChangedYesNoDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setPetitionChangedYesNoDN(translateToStringYesNo(divorceSession.getHasBeenChanges()));
    }

    private String translateToStringYesNo(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }
}
