package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class DivorceCaseToDnCaseMapperUTest {

    @Autowired
    private DivorceCaseToDnCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = (DnCaseData) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/ccd/dn.json", DnCaseData.class);

        DivorceSession divorceSession = (DivorceSession) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/divorce/dn.json", DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }
}
