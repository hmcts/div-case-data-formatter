package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import java.util.List;

public class Mapper {

    public Object transform(String data, String specName) {
        List jsonSpec = JsonUtils.jsonToList( this.getClass().getClassLoader().getResourceAsStream("mapper-specs/" + specName + ".json"));
        Chainr chainrSpec = Chainr.fromSpec(jsonSpec);
        Object inputJSON = JsonUtils.jsonToObject(data);

        Object transformedOutput = chainrSpec.transform( inputJSON );
        return transformedOutput;
    }
}
