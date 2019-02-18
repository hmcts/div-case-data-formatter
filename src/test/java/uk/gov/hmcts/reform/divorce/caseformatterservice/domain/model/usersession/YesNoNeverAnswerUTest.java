package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YesNoNeverAnswerUTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fromInput_converts_yes_successfully() {

        // given
        String yes = "yes";

        // when
        YesNoNeverAnswer yesNoNeverAnswer = objectMapper.convertValue(yes, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.YES, yesNoNeverAnswer);
    }

    @Test
    public void fromInput_converts_no_successfully() {

        // given
        String no = "no";

        // when
        YesNoNeverAnswer yesNoNeverAnswer = objectMapper.convertValue(no, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.NO, yesNoNeverAnswer);
    }

    @Test
    public void fromInput_converts_never_successfully() {

        // given
        String no = "never";

        // when
        YesNoNeverAnswer yesNoNeverAnswer = objectMapper.convertValue(no, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.NEVER, yesNoNeverAnswer);
    }

    @Test
    public void fromInput_throws_exception_for_unrecognized_input() {

        // given
        String maybe = "maybe";

        IllegalArgumentException exception = null;

        // when
        try {
            objectMapper.convertValue(maybe, YesNoNeverAnswer.class);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertNotNull(exception);
        String exceptionMessage = exception.getMessage();

        for (YesNoNeverAnswer answer : YesNoNeverAnswer.values()) {
            assertTrue(exceptionMessage.contains(answer.name()));
        }
    }
}
