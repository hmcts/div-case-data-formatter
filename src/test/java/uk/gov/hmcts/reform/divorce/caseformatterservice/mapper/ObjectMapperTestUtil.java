package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ObjectMapperTestUtil {

    public static Object jsonToObject(final String absoluteFilePath, Class<?> type)
        throws IOException, URISyntaxException {
        return jsonStringToObject(loadJson(absoluteFilePath), type);
    }

    public static String loadJson(final String absoluteFilePath) throws URISyntaxException, IOException {
        URI uri = ObjectMapperTestUtil.class.getClassLoader().getResource(absoluteFilePath).toURI();

        return  new String(Files.readAllBytes(Paths.get(uri)), Charset.forName("utf-8"));
    }

    public static String convertObjectToJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static <T> T jsonStringToObject(final String jsonString, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonString, type);
    }
}
