package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class AosCaseToCCDMapperUTest {

    @Autowired
    private DivorceCaseToAosCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        AosCaseData expectedAosCaseData = (AosCaseData) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/ccd/aos.json", AosCaseData.class);

        DivorceSession divorceSession = (DivorceSession) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/divorce/aos.json", DivorceSession.class);

        AosCaseData actualAosCaseData = mapper.divorceCaseDataToAosCaseData(divorceSession);

        assertThat(actualAosCaseData, samePropertyValuesAs(expectedAosCaseData));
    }
}
