package uk.gov.hmcts.reform.divorce.caseformatterservice.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilUTest {

    @Test
    public void nonNullDateReturnsFormattedDateString() {
        assertEquals("01 01 2001", DateUtil.format(
            Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "dd MM yyyy")
        );
    }

    @Test
    public void nullDateReturnsFormattedZeroDateString() {
        assertEquals("01 01 1970", DateUtil.format(
            null,
            "dd MM yyyy")
        );
    }
}
