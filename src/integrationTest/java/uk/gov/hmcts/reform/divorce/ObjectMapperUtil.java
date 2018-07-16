package uk.gov.hmcts.reform.divorce;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperUtil {

    public static Object jsonStringToObject(final String jsonString, Class<?> type) throws IOException {
        return new ObjectMapper().readValue(jsonString, type);
    }
}
