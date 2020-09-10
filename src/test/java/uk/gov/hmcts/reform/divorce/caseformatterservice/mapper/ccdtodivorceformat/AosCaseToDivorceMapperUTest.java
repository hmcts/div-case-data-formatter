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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.util.Constants.NO_VALUE;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.util.Constants.YES_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class AosCaseToDivorceMapperUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/aos.json", CoreCaseData.class);

        DivorceSession expectedDivorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/divorce/aos.json", DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, samePropertyValuesAs(expectedDivorceSession));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenValueIsYes() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setRespContactMethodIsDigital(YES_VALUE);

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(true));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenValueIsNo() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setRespContactMethodIsDigital(NO_VALUE);

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(false));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenNotSet() {
        CoreCaseData coreCaseData = new CoreCaseData();

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(nullValue()));
    }
}
