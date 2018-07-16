package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.junit.annotations.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RunWith(SerenityParameterizedRunner.class)
public class CCDToDivorceFormatTest extends IntegrationTest {
    private static final String PAYLOAD_CONTEXT_PATH = "fixtures/ccdtodivorcemapping/ccd/";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/ccdtodivorcemapping/divorce/";

    @Value("${case.formatter.service.transform.todivorceformat}")
    private String contextPath;

    private final String input;
    private final String expected;

    public CCDToDivorceFormatTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @TestData
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
            {"additionalpayment.json", "additional-payment.json"},
            {"addresscase.json", "addresses.json"},
            {"hownamechanged.json", "how-name-changed.json"},
            {"jurisdiction612.json", "jurisdiction-6-12.json"},
            {"jurisdictionall.json", "jurisdiction-all.json"},
            {"paymentcase.json", "payment.json"},
            {"reasonadultery.json", "reason-adultery.json"},
            {"reasondesertion.json", "reason-desertion.json"},
            {"reasonseparation.json", "reason-separation.json"},
            {"reasonunreasonablebehaviour.json", "reason-unreasonable-behaviour.json"},
            {"samesex.json", "same-sex.json"}
        });
    }

    @Test
    public void whenTransformToDivorceFormat_thenReturnExpected() throws Exception {
        final Response response = RestUtil.postToRestService(getAPIPath(),
            getHeaders(),
            ResourceLoader.loadJson(PAYLOAD_CONTEXT_PATH + input));

        final Map<String, Object> expectedOutput = getExpected(expected);

        final Map<String, Object> actualOutput = getActual(response.getBody().asString());

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getExpected(String fileName) throws Exception {
        return (Map<String, Object>)ObjectMapperUtil.jsonStringToObject(
            ResourceLoader.loadJson(EXPECTED_PAYLOAD_CONTEXT_PATH + fileName), Map.class);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getActual(String json) throws Exception {
        return (Map<String, Object>)ObjectMapperUtil.jsonStringToObject(json, Map.class);
    }

    @Override
    String getContextPath() {
        return contextPath;
    }
}
