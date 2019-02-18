package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Locale;
import java.util.Objects;

public class MappingCommons {

    private MappingCommons() {
    }

    private static final String NEVER = "NEVER";

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static String translateToAnswer(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value.equalsIgnoreCase(NEVER)) {
            return NEVER;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }

}
