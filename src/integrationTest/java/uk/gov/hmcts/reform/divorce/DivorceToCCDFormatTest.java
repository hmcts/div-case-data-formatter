package uk.gov.hmcts.reform.divorce;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.junit.annotations.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@RunWith(SerenityParameterizedRunner.class)
public class DivorceToCCDFormatTest extends IntegrationTest {
    private static final String PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/divorce/";
    private static final String EXPECTED_PAYLOAD_CONTEXT_PATH = "fixtures/divorcetoccdmapping/ccd/";

    @Value("${case.formatter.service.transform.toccdformat.context-path}")
    private String contextPath;

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
            getHeaders(getUserToken()),
            ResourceLoader.loadJson(PAYLOAD_CONTEXT_PATH + input));

        final Map<String, Object> expectedOutput = getExpected(expected);

        final String dateString =
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
        expectedOutput.put("createdDate", dateString);
        expectedOutput.put("D8PetitionerEmail", EMAIL_ADDRESS);

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
}
