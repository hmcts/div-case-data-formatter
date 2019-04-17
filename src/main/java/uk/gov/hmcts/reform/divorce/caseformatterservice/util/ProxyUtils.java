package uk.gov.hmcts.reform.divorce.caseformatterservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class ProxyUtils {

    public static void useProxy() {
        System.setProperty("http.proxyHost", "proxyout.reform.hmcts.net");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "proxyout.reform.hmcts.net");
        System.setProperty("https.proxyPort", "8080");
    }

    public static String printAsJsonString(Object elem) {
        try {
            String elemString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(elem);
            System.out.println("==============");
            System.out.println(elemString);
            return  elemString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void printDiff(Map input, Map expected) {
        input.forEach((key, value) -> {
            if (!Objects.equals(value, expected.get(key))) {
                System.out.println("=======================");
                System.out.println(key.toString());
                System.out.println("Expected : " + value);
                System.out.println("Actual : " + expected.get(key));
            }
        });
    }
}
