package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = DocumentCollectionCCDFormatMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDnCaseMapper {

    //@Mapping(source = "DocumentsUploadedDN", target = "DocumentsUploadedDN")
    @Mapping(source = "changesDetails", target = "petitionChangedDetailsDN")
    @Mapping(source = "claimCosts", target = "divorceCostsOptionDN")
    @Mapping(source = "statementOfTruth", target = "statementOfTruthDN")
    @Mapping(source = "intolerable", target = "adulteryLifeIntolerable")
    @Mapping(source = "adulteryFirstFoundDate", target = "adulteryDateFoundOut")
    @Mapping(source = "livedApartSinceAdultery", target = "adulteryLivedApartSinceEventDN")
    @Mapping(source = "datesLivedTogether", target = "adulteryTimeLivedTogetherDetailsDN")
    @Mapping(source = "behaviourContinuedSinceApplication", target = "behaviourStillHappeningDN")
    @Mapping(source = "lastIncidentDate", target = "behaviourMostRecentIncidentDateDN")
    @Mapping(source = "livedApartSinceLastIncidentDate", target = "behaviourLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "behaviourTimeLivedTogetherDetailsDN")
    @Mapping(source = "livedApartSinceDesertion", target = "desertionLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "desertionTimeLivedTogetherDetailsDN")
    @Mapping(source = "livedApartSinceSeparation", target = "separationLivedApartSinceEventDN")
    @Mapping(source = "approximateDatesOfLivingTogetherField", target = "separationTimeLivedTogetherDetailsDN")
    public abstract DnCaseData divorceCaseDataToDnCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void mapDnApplicationSubmittedDate(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDnApplicationSubmittedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @AfterMapping
    protected void mapConfirmPetitionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        if (
            divorceSession.getStatementOfTruth().equalsIgnoreCase("YES")
                || divorceSession.getStatementOfTruthNoChanges().equalsIgnoreCase("NO")
            ) {
            result.setConfirmPetitionDN("YES");
        } else {
            result.setConfirmPetitionDN("NO");
        }
    }

}
