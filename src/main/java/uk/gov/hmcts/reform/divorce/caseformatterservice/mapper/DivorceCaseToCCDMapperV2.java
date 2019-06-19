package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import com.bazaarvoice.jolt.Transform;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Component
public class DivorceCaseToCCDMapperV2 extends Mapper implements Transform {
    public Object transformToCCDFormat(String divorceSession) {
        return transform(divorceSession, "divorce-case-to-ccd-spec");
    }

    @Override
    public Object transform( final Object input ) {
        setCreatedDateToToday(input);
        return input;
    }

    private void setCreatedDateToToday(Object input) {
        ((Map) input).put("createdDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
