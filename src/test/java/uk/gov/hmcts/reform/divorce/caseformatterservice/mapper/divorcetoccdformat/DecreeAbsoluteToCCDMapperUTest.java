package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDaCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class DecreeAbsoluteToCCDMapperUTest {

    @Autowired
    private DivorceCaseToDaCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        DaCaseData expectedDaCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/da.json", DaCaseData.class);

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/da.json", DivorceSession.class);

        DaCaseData actualDaCaseData = mapper.divorceCaseDataToDaCaseData(divorceSession);

        assertThat(actualDaCaseData, samePropertyValuesAs(expectedDaCaseData));
    }

}