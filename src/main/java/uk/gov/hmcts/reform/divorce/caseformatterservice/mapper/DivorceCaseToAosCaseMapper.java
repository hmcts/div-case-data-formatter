package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.corespondent.CoRespondentAnswers;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.SIMPLE_DATE_FORMAT;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.translateToAnswer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToAosCaseMapper {

    @Mapping(source = "coRespondentAnswers.confirmReadPetition", target = "coRespConfirmReadPetition")
    @Mapping(source = "coRespondentAnswers.admitAdultery", target = "coRespAdmitAdultery")
    @Mapping(source = "coRespondentAnswers.contactInfo.consentToReceivingEmails", target = "coRespConsentToEmail")
    @Mapping(source = "coRespondentAnswers.contactInfo.contactMethodIsDigital", target = "coRespContactMethodIsDigital")
    @Mapping(source = "coRespondentAnswers.costs.agreeToCosts", target = "coRespAgreeToCosts")
    @Mapping(source = "coRespondentAnswers.costs.reason", target = "coRespCostsReason")
    @Mapping(source = "coRespondentAnswers.defendsDivorce", target = "coRespDefendsDivorce")
    @Mapping(source = "coRespondentAnswers.contactInfo.emailAddress", target = "coRespEmailAddress")
    @Mapping(source = "coRespondentAnswers.contactInfo.phoneNumber", target = "coRespPhoneNumber")
    @Mapping(source = "coRespondentAnswers.statementOfTruth", target = "coRespStatementOfTruth")
    @Mapping(source = "coRespondentAnswers.answer.received", target = "receivedAnswerFromCoResp")
    @Mapping(source = "coRespondentAnswers.answer.dateReceived", target = "receivedAnswerFromCoRespDate",
            dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "coRespondentAnswers.aos.received", target = "receivedAosFromCoResp")
    @Mapping(source = "coRespondentAnswers.aos.dateReceived", target = "receivedAosFromCoRespDate",
            dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "coRespondentAnswers.aos.dueDate", target = "dueDateCoResp",
            dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "coRespondentAnswers.aos.letterHolderId", target = "coRespLetterHolderId")
    public abstract AosCaseData divorceCaseDataToAosCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void removeRespEmailAddress(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        result.setRespEmailAddress(null);
    }

    @AfterMapping
    protected void setDigital(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        result.setRespContactMethodIsDigital("YES");
    }

    @AfterMapping
    protected void mapCoRespondentFields(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        CoRespondentAnswers coRespondentAnswers = divorceSession.getCoRespondentAnswers();

        if (coRespondentAnswers != null) {
            result.setReceivedAosFromCoResp(
                translateToAnswer(coRespondentAnswers.getAnswer().getReceived()));
            result.setReceivedAnswerFromCoResp(
                translateToAnswer(coRespondentAnswers.getAnswer().getReceived()));
            result.setCoRespConfirmReadPetition(
                translateToAnswer(coRespondentAnswers.getConfirmReadPetition()));
            result.setCoRespStatementOfTruth(translateToAnswer(coRespondentAnswers.getStatementOfTruth()));
            result.setCoRespAdmitAdultery(translateToAnswer(coRespondentAnswers.getAdmitAdultery()));
            result.setCoRespDefendsDivorce(translateToAnswer(coRespondentAnswers.getDefendsDivorce()));
            result.setCoRespAgreeToCosts(translateToAnswer(coRespondentAnswers.getCosts().getAgreeToCosts()));
            result.setCoRespConsentToEmail(
                    translateToAnswer(coRespondentAnswers.getContactInfo().getConsentToReceivingEmails()));
            result.setCoRespContactMethodIsDigital(
                    translateToAnswer(coRespondentAnswers.getContactInfo().getContactMethodIsDigital()));
        }
    }

}
