package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ccdtodivorceformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class LanguagePreferenceWelshCaseToDivorceMapperUTest extends ObjectMapperTestUtil {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForReasonUnreasonableBehaviourScenario()
        throws URISyntaxException, IOException {

        CoreCaseData caseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/languagepreferencewelsh.json",
                CoreCaseData.class);

        DivorceSession expectedDivorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(
            "fixtures/ccdtodivorcemapping/divorce/language-preference-welsh.json",
            DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(caseData);

        assertThat(actualDivorceSession, samePropertyValuesAs(expectedDivorceSession));
    }
}
