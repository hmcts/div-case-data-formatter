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
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
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
public class GetAosCaseDataITest {
    private static final String API_URL = "/caseformatter/version/1/to-aos-submit-format";
    private static final String PAYLOAD_PATH = "fixtures/divorcetoccdmapping/divorce/aos.json";
    private static final String EXPECTED_PAYLOAD_PATH = "fixtures/divorcetoccdmapping/ccd/aos.json";

    @Autowired
    private MockMvc webClient;

    @Test
    public void givenCaseDataIsNull_whenGetAosCaseData_thenReturnBadRequest() throws Exception {
        webClient.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidDetails_whenGetAosCaseData_thenReturnExpected() throws Exception {
        final AosCaseData expectedAosCaseData =
                ObjectMapperTestUtil.retrieveFileContentsAsObject(EXPECTED_PAYLOAD_PATH, AosCaseData.class);

        MvcResult result = webClient.perform(post(API_URL)
                .content(ObjectMapperTestUtil.retrieveFileContents(PAYLOAD_PATH))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AosCaseData actualAosCaseData =
                ObjectMapperTestUtil.convertJsonToObject(result.getResponse().getContentAsString(), AosCaseData.class);

        assertThat(actualAosCaseData, samePropertyValuesAs(expectedAosCaseData));
    }
}
