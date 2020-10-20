package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ccdtodivorceformat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.io.IOException;
import java.net.URISyntaxException;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.convertObjectToJson;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.retrieveFileContentsAsObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class AddressesCaseToDivorceMapperUTest {

    private static final String CCD_CASE_DATA_PATH = "fixtures/ccdtodivorcemapping/ccd/addresscase.json";
    private static final String EXPECTED_DIVORCE_SESSION_PATH = "fixtures/ccdtodivorcemapping/divorce/addresses.json";

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForAdulteryDifferentAddressMappingScenario()
        throws URISyntaxException, IOException, JSONException {

        CoreCaseData coreCaseData = retrieveFileContentsAsObject(CCD_CASE_DATA_PATH, CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        DivorceSession expectedDivorceSession = retrieveFileContentsAsObject(EXPECTED_DIVORCE_SESSION_PATH, DivorceSession.class);
        JSONAssert.assertEquals(convertObjectToJson(expectedDivorceSession), convertObjectToJson(actualDivorceSession), true);
    }

}
