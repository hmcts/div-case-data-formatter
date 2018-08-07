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
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CaseFormatterServiceApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
public class AddDocumentsITest {
    private static final String API_URL = "/caseformatter/version/1/add-documents";
    private static final String PAYLOAD_PATH = "fixtures/model/ccd/addDocumentsInput.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/model/ccd/addDocumentsOutput.json";

    @Autowired
    private MockMvc webClient;

    @Test
    public void givenCaseDataIsNull_whenAddDocuments_thenReturnBadRequest() throws Exception {
        webClient.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidDetails_whenAddDocuments_thenReturnExpected() throws Exception {
        final CoreCaseData expectedCaseData =
            (CoreCaseData)ObjectMapperTestUtil.jsonToObject(EXPECTED_PAYLOAD_PATH, CoreCaseData.class);

        MvcResult result = webClient.perform(post(API_URL)
            .content(ObjectMapperTestUtil.loadJson(PAYLOAD_PATH))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final CoreCaseData actualCaseData =
            (CoreCaseData)ObjectMapperTestUtil.jsonStringToObject(result.getResponse().getContentAsString(),
                CoreCaseData.class);

        assertThat(expectedCaseData, samePropertyValuesAs(actualCaseData));
    }
}
