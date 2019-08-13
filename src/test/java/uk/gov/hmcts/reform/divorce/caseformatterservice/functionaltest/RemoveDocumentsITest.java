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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DocumentType.PETITION;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CaseFormatterServiceApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
public class RemoveDocumentsITest {
    private static final String API_URL = "/caseformatter/version/1/remove/documents/";
    private static final String PAYLOAD_PATH = "fixtures/model/ccd/removeDocumentsInput.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/model/ccd/removeDocumentsOutput.json";

    @Autowired
    private MockMvc webClient;

    @Test
    public void givenCaseDataIsNull_whenRemoveDocuments_thenReturnBadRequest() throws Exception {
        webClient.perform(post(API_URL + PETITION)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidDetails_whenRemoveDocuments_thenReturnExpected() throws Exception {
        final Map<String, Object> expectedCaseData =
            ObjectMapperTestUtil.retrieveFileContentsAsObject(EXPECTED_PAYLOAD_PATH, Map.class);

        MvcResult result = webClient.perform(post(API_URL + PETITION)
            .content(ObjectMapperTestUtil.retrieveFileContents(PAYLOAD_PATH))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final Map<String, Object> actualCaseData =
            ObjectMapperTestUtil.convertJsonToObject(result.getResponse().getContentAsString(),
                Map.class);

        assertThat(actualCaseData).isEqualTo(expectedCaseData);
    }
}
