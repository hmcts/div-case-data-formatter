package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Locale;

public final class MappingCommons {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    private MappingCommons() {
    }

    public static String toYesNoUpperCase(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return toYesNo(value).toUpperCase(Locale.ENGLISH);
    }

    public static String toYesNoPascalCase(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, toYesNo(value));
    }

    private static String toYesNo(String value) {
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value));
    }
}
