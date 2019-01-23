package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Locale;
import java.util.Objects;

public class MappingCommons {

    private MappingCommons() {
    }

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static String translateToStringYesNo(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }

}