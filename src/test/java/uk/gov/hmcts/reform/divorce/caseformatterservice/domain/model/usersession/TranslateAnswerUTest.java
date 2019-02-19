package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TranslateAnswerUTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fromInput_converts_yes_successfully() {

        // given
        String yes = "yes";

        // when
        TranslateAnswer translateAnswer = objectMapper.convertValue(yes, TranslateAnswer.class);

        // then
        assertEquals(TranslateAnswer.YES, translateAnswer);
    }

    @Test
    public void fromInput_converts_no_successfully() {

        // given
        String no = "no";

        // when
        TranslateAnswer translateAnswer = objectMapper.convertValue(no, TranslateAnswer.class);

        // then
        assertEquals(TranslateAnswer.NO, translateAnswer);
    }

    @Test
    public void fromInput_converts_never_successfully() {

        // given
        String no = "never";

        // when
        TranslateAnswer translateAnswer = objectMapper.convertValue(no, TranslateAnswer.class);

        // then
        assertEquals(TranslateAnswer.NEVER, translateAnswer);
    }

    @Test
    public void fromInput_throws_exception_for_unrecognized_input() {

        // given
        String maybe = "maybe";

        IllegalArgumentException exception = null;

        // when
        try {
            objectMapper.convertValue(maybe, TranslateAnswer.class);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertNotNull(exception);
        String exceptionMessage = exception.getMessage();

        for (TranslateAnswer answer : TranslateAnswer.values()) {
            assertTrue(exceptionMessage.contains(answer.name()));
        }
    }
}
