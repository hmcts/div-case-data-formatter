package uk.gov.hmcts.reform.divorce.caseformatterservice.functionaltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.convertJsonToObject;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.convertObjectToJson;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.retrieveFileContents;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.retrieveFileContentsAsObject;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CaseFormatterServiceApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
public class TransformToDivorceFormatITest {

    private static final String API_URL = "/caseformatter/version/1/to-divorce-format";
    private static final String PAYLOAD_PATH = "fixtures/ccdtodivorcemapping/ccd/case-data.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/ccdtodivorcemapping/divorce/case-data.json";

    @Autowired
    private MockMvc webClient;

    @Test
    public void givenCaseDataIsNull_whenTransformToDivorceFormat_thenReturnBadRequest() throws Exception {
        webClient.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidDetails_whenTransformToDivorceFormat_thenReturnExpected() throws Exception {
        MvcResult result = webClient.perform(post(API_URL)
            .content(retrieveFileContents(PAYLOAD_PATH))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final DivorceSession actualDivorceSession =
            convertJsonToObject(result.getResponse().getContentAsString(), DivorceSession.class);

        final DivorceSession expectedDivorceSession =
            retrieveFileContentsAsObject(EXPECTED_PAYLOAD_PATH, DivorceSession.class);
        JSONAssert.assertEquals(convertObjectToJson(expectedDivorceSession),
            convertObjectToJson(actualDivorceSession), true);
    }

}
