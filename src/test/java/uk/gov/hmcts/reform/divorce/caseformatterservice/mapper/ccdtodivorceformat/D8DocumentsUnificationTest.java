package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ccdtodivorceformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.helper.DocumentsTestHelper.createDocumentCollectionMember;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.helper.DocumentsTestHelper.createGeneralOrderCollectionMember;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil.retrieveFileContentsAsObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class D8DocumentsUnificationTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldUnifyDocumentsWhenConvertingCcdToDivorceFormat() throws IOException, URISyntaxException {
        CoreCaseData coreCaseData = retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/generated-documents.json", CoreCaseData.class);
        assertThat(coreCaseData.getD8Documents(), hasSize(1));
        assertThat(coreCaseData.getServiceApplicationDocuments(), hasSize(2));
        assertThat(coreCaseData.getGeneralOrders(), hasSize(2));

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        String divorceSessionJson = ObjectMapperTestUtil.convertObjectToJson(divorceSession);
        assertThat(divorceSessionJson, hasJsonPath("$.d8", hasSize(5)));
        assertThat(divorceSessionJson, hasJsonPath("$.d8[0]",
            allOf(
                hasJsonPath("id", is("560f4bd1-fcf2-4dc5-80d1-d7e00d861683")),
                hasJsonPath("fileName", is("d8-eng.pdf")),
                hasJsonPath("fileUrl", is("http://localhost:5006/documents/560f4bd1-fcf2-4dc5-80d1-d7e00d861683"))
            )
        ));
        assertThat(divorceSessionJson, hasJsonPath("$.d8[1]",
            allOf(
                hasJsonPath("id", is("04e78b6b-3a6f-4db7-b0c5-f8eeb814a83d")),
                hasJsonPath("fileName", is("deemedServiceRefused2020-09-28.pdf")),
                hasJsonPath("fileUrl", is("http://localhost:5006/documents/04e78b6b-3a6f-4db7-b0c5-f8eeb814a83d"))
            )
        ));
        assertThat(divorceSessionJson, hasJsonPath("$.d8[2]",
            allOf(
                hasJsonPath("id", is("c4ca7cbc-adb2-4908-b522-ad69961ae9ff")),
                hasJsonPath("fileName", is("deemedServiceRefused2020-08-01.pdf")),
                hasJsonPath("fileUrl", is("http://localhost:5006/documents/c4ca7cbc-adb2-4908-b522-ad69961ae9ff"))
            )
        ));
        assertThat(divorceSessionJson, hasJsonPath("$.d8[3]",
            allOf(
                hasJsonPath("id", is("9320ad41-4980-47b7-885d-76daae69ff9b")),
                hasJsonPath("fileName", is("generalOrder2020-09-28.pdf")),
                hasJsonPath("fileUrl", is("http://localhost:5006/documents/9320ad41-4980-47b7-885d-76daae69ff9b"))
            )
        ));
        assertThat(divorceSessionJson, hasJsonPath("$.d8[4]",
            allOf(
                hasJsonPath("id", is("649111b0-1aad-446d-b979-27f5862af583")),
                hasJsonPath("fileName", is("generalOrder2020-09-29.pdf")),
                hasJsonPath("fileUrl", is("http://localhost:5006/documents/649111b0-1aad-446d-b979-27f5862af583"))
            )
        ));
    }

    @Test
    public void shouldNotFailTransformationIfCoreCaseDataHasNoDocumentsToUnify() {
        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(new CoreCaseData());

        assertThat(divorceSession, is(notNullValue()));
    }

    @Test
    public void shouldNotFailTransformationIfCoreCaseDataHasNoD8DocumentsButHasServiceApplication() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setServiceApplicationDocuments(singletonList(createDocumentCollectionMember("url", "fileName", "2020-12-12")));

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getD8Documents(), hasSize(1));
    }

    @Test
    public void shouldNotFailTransformationIfCoreCaseDataHasNoD8DocumentsButHasGeneralOrder() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setGeneralOrders(singletonList(createGeneralOrderCollectionMember("url", "fileName", "2020-12-12")));

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getD8Documents(), hasSize(1));
    }

    @Test
    public void shouldNotFailTransformationIfCoreCaseDataHasNoD8DocumentsAndOtherDocumentsComeAsEmptyLists() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setGeneralOrders(emptyList());
        coreCaseData.setServiceApplicationDocuments(emptyList());

        DivorceSession divorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getD8Documents(), is(nullValue()));
    }

}
