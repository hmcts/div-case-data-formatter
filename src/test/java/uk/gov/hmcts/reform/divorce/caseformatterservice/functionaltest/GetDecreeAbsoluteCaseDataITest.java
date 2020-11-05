package uk.gov.hmcts.reform.divorce.caseformatterservice.functionaltest;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.DaCaseData;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CaseFormatterServiceApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
public class GetDecreeAbsoluteCaseDataITest {
    private static final String API_URL = "/caseformatter/version/1/to-da-submit-format";
    private static final String PAYLOAD_PATH = "fixtures/divorcetoccdmapping/divorce/da.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/divorcetoccdmapping/ccd/da.json";

    @Autowired
    private MockMvc webClient;

    @Test
    public void givenCaseDataIsNull_whenGetDaCaseData_thenReturnBadRequest() throws Exception {
        webClient.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidDetails_whenGetDaCaseData_thenReturnExpected() throws Exception {
        final DaCaseData expectedDaCaseData =
            ObjectMapperTestUtil.retrieveFileContentsAsObject(EXPECTED_PAYLOAD_PATH, DaCaseData.class);

        MvcResult result = webClient.perform(post(API_URL)
            .content(ObjectMapperTestUtil.retrieveFileContents(PAYLOAD_PATH))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final DaCaseData actualDaCaseData =
            ObjectMapperTestUtil.convertJsonToObject(result.getResponse().getContentAsString(), DaCaseData.class);

        assertEquals(expectedDaCaseData, actualDaCaseData);
    }
}
