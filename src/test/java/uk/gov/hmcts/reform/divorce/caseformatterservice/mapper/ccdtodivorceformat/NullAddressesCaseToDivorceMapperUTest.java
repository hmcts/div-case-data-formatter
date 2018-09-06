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
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class NullAddressesCaseToDivorceMapperUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForAdulteryDifferentAddressMappingScenario()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = (CoreCaseData) ObjectMapperTestUtil
            .jsonToObject("fixtures/ccdtodivorcemapping/ccd/addressnullcase.json", CoreCaseData.class);

        DivorceSession expectedDivorceSession = (DivorceSession) ObjectMapperTestUtil
            .jsonToObject("fixtures/ccdtodivorcemapping/divorce/addresses-null.json", DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, samePropertyValuesAs(expectedDivorceSession));
    }

    @Test
    public void shouldMapAllAndTransformAllFieldsForAdulteryDifferentAddressMappingScenarioWithDerivedNotNull()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = (CoreCaseData) ObjectMapperTestUtil
            .jsonToObject("fixtures/ccdtodivorcemapping/ccd/addressnullderivednotnullcase.json",
                CoreCaseData.class);

        DivorceSession expectedDivorceSession = (DivorceSession) ObjectMapperTestUtil
            .jsonToObject("fixtures/ccdtodivorcemapping/divorce/addresses-null.json", DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, samePropertyValuesAs(expectedDivorceSession));
    }
}
