package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.junit.annotations.TestData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RunWith(SerenityParameterizedRunner.class)
public class DivorceToCCDFormatTest extends IntegrationTest {
    private static final String PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/divorce/";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/ccd/";
    private static final String URL_REGEX = ".*?(/documents/.*)";
    private static final String DOCUMENTS_UPLOADED_JSON_KEY = "D8DocumentsUploaded";
    private static final String DOCUMENTS_GENERATED_JSON_KEY = "D8DocumentsGenerated";
    private static final String VALUE_JSON_KEY = "value";

    @Value("${case.formatter.service.transform.toccdformat.context-path}")
    private String contextPath;

    @Value("${document.management.store.url}")
    private String documentManagementStoreUrl;

    private final String input;
    private final String expected;

    public DivorceToCCDFormatTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @TestData
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
            {"additional-payment.json", "additionalpayment.json"},
            {"addresses.json", "addresscase.json"},
            {"d8-document.json", "d8document.json"},
            {"how-name-changed.json", "hownamechanged.json"},
            {"jurisdiction-6-12.json", "jurisdiction612.json"},
            {"jurisdiction-all.json", "jurisdictionall.json"},
            {"overwrite-payment.json", "overwritepayment.json"},
            {"payment-id-only.json", "paymentidonly.json"},
            {"payment.json", "paymentcase.json"},
            {"reason-adultery.json", "reasonadultery.json"},
            {"reason-desertion.json", "reasondesertion.json"},
            {"reason-separation.json", "reasonseparation.json"},
            {"reason-unreasonable-behaviour.json", "reasonunreasonablebehaviour.json"},
            {"same-sex.json", "samesex.json"}
        });
    }

    @Test
    public void whenTransformToCCDFormat_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(getAPIPath(),
            getHeaders("remove_me"),
            ResourceLoader.loadJson(PAYLOAD_CONTEXT_PATH + input));

        final Map<String, Object> expectedOutput = getExpected(expected);

        final String dateString =
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
        expectedOutput.put("createdDate", dateString);

        updateDocumentsInOutput(DOCUMENTS_UPLOADED_JSON_KEY, expectedOutput);
        updateDocumentsInOutput(DOCUMENTS_GENERATED_JSON_KEY, expectedOutput);

        final Map<String, Object> actualOutput = getActual(response.getBody().asString());

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Override
    String getContextPath() {
        return contextPath;
    }

    @Override
    String getExpectedContextPath() {
        return EXPECTED_PAYLOAD_CONTEXT_PATH;
    }

    private void updateDocumentsInOutput(String jsonKey, Map<String, Object> expectedOutput) {
        List<Map<String, Object>> listOfDocuments =
            (List<Map<String, Object>>) expectedOutput.get(jsonKey);

        if (listOfDocuments != null) {
            listOfDocuments.forEach(collectionMember ->
                collectionMember.put(VALUE_JSON_KEY,
                    updateExpectedDocumentUrl((Map<String, Object>) collectionMember.get(VALUE_JSON_KEY))
                )
            );

            expectedOutput.put(jsonKey, listOfDocuments);
        }
    }

    private Map<String, Object> updateExpectedDocumentUrl(Map<String, Object> document) {
        final String documentLinkJsonKey = "DocumentLink";
        final String documentUrlJsonKey = "document_url";

        Map<String, Object> documentLink = (Map<String, Object>) document.get(documentLinkJsonKey);
        String documentUrl = (String) documentLink.get(documentUrlJsonKey);

        if (StringUtils.isNotEmpty(documentUrl)) {
            documentLink.put(documentUrlJsonKey, documentUrl
                .replaceAll(URL_REGEX, documentManagementStoreUrl + "$1"));
        }

        document.put(documentLinkJsonKey, documentLink);
        return document;
    }
}
