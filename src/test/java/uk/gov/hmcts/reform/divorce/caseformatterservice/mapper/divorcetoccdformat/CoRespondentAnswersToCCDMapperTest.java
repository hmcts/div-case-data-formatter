package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.divorcetoccdformat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;

import java.io.IOException;
import java.net.URISyntaxException;

import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.convertObjectToJson;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.retrieveFileContentsAsObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class CoRespondentAnswersToCCDMapperTest {

    private static final String JSON_EXAMPLES_ROOT_FOLDER = "fixtures/divorcetoccdmapping/";

    @Autowired
    private DivorceCaseToAosCaseMapper mapper;

    @Test
    public void shouldMapAllFieldsForCoRespondent()
            throws URISyntaxException, IOException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers.json",
                "ccd/co-respondent/co-respondent-answers.json");
    }

    @Test
    public void shouldMapFieldsForCoRespondent_UsingIncompleteNestedStructure()
            throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers-incomplete.json",
                "ccd/co-respondent/co-respondent-answers-incomplete.json");
    }

    @Test
    public void shouldMapFields_WhenCoRespondentSectionDoesNotExist()
            throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers-empty.json",
                "ccd/co-respondent/co-respondent-answers-empty.json");
    }

    private void testJsonResultsAreAsExpected(String divorceJsonFilePath, String ccdJsonFilePath)
            throws IOException, URISyntaxException, JSONException {

        DivorceSession divorceSession = retrieveFileContentsAsObject(JSON_EXAMPLES_ROOT_FOLDER + divorceJsonFilePath,
                DivorceSession.class);

        AosCaseData actualAosCaseData = mapper.divorceCaseDataToAosCaseData(divorceSession);

        AosCaseData expectedCCD = retrieveFileContentsAsObject(JSON_EXAMPLES_ROOT_FOLDER + ccdJsonFilePath,
                AosCaseData.class);

        JSONAssert.assertEquals(convertObjectToJson(expectedCCD),
                convertObjectToJson(actualAosCaseData),
                false);
    }

}