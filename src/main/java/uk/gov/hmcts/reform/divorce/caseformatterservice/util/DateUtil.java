package uk.gov.hmcts.reform.divorce.caseformatterservice.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Optional;

public class DateUtil {

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(
            Optional.ofNullable(date).orElse(new Date(0)),
            pattern
        );
    }
}