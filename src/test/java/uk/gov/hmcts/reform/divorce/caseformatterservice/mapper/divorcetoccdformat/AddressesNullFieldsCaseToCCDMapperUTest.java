package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class AddressesNullFieldsCaseToCCDMapperUTest {

    @Autowired
    private DivorceCaseToCCDMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForAddressWithNullValuesMappingScenario()
        throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = (CoreCaseData) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/ccd/nulladdressfields.json", CoreCaseData.class);
        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = (DivorceSession) ObjectMapperTestUtil
            .jsonToObject("fixtures/divorcetoccdmapping/divorce/null-address-fields.json", DivorceSession.class);

        CoreCaseData actualCoreCaseData = mapper.divorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }
}