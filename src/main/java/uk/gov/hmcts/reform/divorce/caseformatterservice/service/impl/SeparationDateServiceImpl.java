package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.SeparationDateService;

import java.util.Date;

@Service
public class SeparationDateServiceImpl implements SeparationDateService {

    @Override
    public void updateSeparationDate(DivorceSession divorceSession) {
        Date separationDate = divorceSession.getReasonForDivorceDecisionDate();

        if (separationDate == null
            || (divorceSession.getReasonForDivorceLivingApartDate() != null
            && separationDate.before(divorceSession.getReasonForDivorceLivingApartDate()))) {
            separationDate = divorceSession.getReasonForDivorceLivingApartDate();
        }

        if (separationDate == null) {
            separationDate = divorceSession.getReasonForDivorceSeperationDate();
        }

        divorceSession.setReasonForDivorceSeperationDate(separationDate);
    }
}
