package uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum YesNoAnswer {

    YES("Yes"),
    NO("No");

    private final String answer;

    @JsonCreator
    public static YesNoAnswer fromInput(String input) {
        if (input.equalsIgnoreCase(YES.getAnswer())) {
            return YES;
        } else if (input.equalsIgnoreCase(NO.getAnswer())) {
            return NO;
        }
        throw new IllegalArgumentException(
                String.format("Could not find match for input '%s' in %s",
                        input,
                        Arrays.asList(YesNoAnswer.values())));
    }
}
