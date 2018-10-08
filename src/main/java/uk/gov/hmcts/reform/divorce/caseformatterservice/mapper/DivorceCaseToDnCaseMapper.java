package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDnCaseMapper {

    public abstract DnCaseData divorceCaseDataToDnCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void setDnApplicationSubmittedDate(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDnApplicationSubmittedDate(null);
    }

    @AfterMapping
    protected void setPetitionChangedDetailsDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setPetitionChangedDetailsDN(null);
    }

    @AfterMapping
    protected void setConfirmPetitionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setConfirmPetitionDN(null);
    }

    @AfterMapping
    protected void setDivorceCostsOptionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDivorceCostsOptionDN(null);
    }

    @AfterMapping
    protected void setCostsDifferentDetails(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setCostsDifferentDetails(null);
    }

    @AfterMapping
    protected void setDocumentsUploadedDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDocumentsUploadedDN(null);
    }

    @AfterMapping
    protected void setStatementOfTruthDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setStatementOfTruthDN(null);
    }

    @AfterMapping
    protected void setAlternativeRespCorrAddress(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setAlternativeRespCorrAddress(null);
    }

    @AfterMapping
    protected void setAdulteryLifeIntolerable(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setAdulteryLifeIntolerable(null);
    }

    @AfterMapping
    protected void setAdulteryDateFoundOut(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setAdulteryDateFoundOut(null);
    }

    @AfterMapping
    protected void setAdulteryLivedApartSinceEventDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setAdulteryLivedApartSinceEventDN(null);
    }

    @AfterMapping
    protected void setAdulteryTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                         @MappingTarget DnCaseData result) {
        result.setAdulteryTimeLivedTogetherDetailsDN(null);
    }

    @AfterMapping
    protected void setBehaviourStillHappeningDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setBehaviourStillHappeningDN(null);
    }

    @AfterMapping
    protected void setBehaviourMostRecentIncidentDateDN(DivorceSession divorceSession,
                                                        @MappingTarget DnCaseData result) {
        result.setBehaviourMostRecentIncidentDateDN(null);
    }

    @AfterMapping
    protected void setBehaviourLivedApartSinceEventDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setBehaviourLivedApartSinceEventDN(null);
    }

    @AfterMapping
    protected void setBehaviourTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {
        result.setBehaviourTimeLivedTogetherDetailsDN(null);
    }

    @AfterMapping
    protected void setDesertionLivedApartSinceEventDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDesertionLivedApartSinceEventDN(null);
    }

    @AfterMapping
    protected void setDesertionTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {
        result.setDesertionTimeLivedTogetherDetailsDN(null);
    }

    @AfterMapping
    protected void setSeparationLivedApartSinceEventDN(DivorceSession divorceSession,
                                                       @MappingTarget DnCaseData result) {
        result.setSeparationLivedApartSinceEventDN(null);
    }

    @AfterMapping
    protected void setSeparationTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                           @MappingTarget DnCaseData result) {
        result.setSeparationTimeLivedTogetherDetailsDN(null);
    }

}
