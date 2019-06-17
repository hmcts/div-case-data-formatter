package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import com.bazaarvoice.jolt.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class DivorceCaseToCCDMapperV2 extends Mapper {
    public Object transformToCCDFormat(String divorceSession) {
        Object output = transform(divorceSession, "divorce-case-to-ccd-spec");
        output = applyPostMappingTransformationTo(output);
        return output;
    }

    private Object applyPostMappingTransformationTo(Object divorceSession) {
        return divorceSession;
    }
}
