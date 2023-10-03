package uk.gov.hmcts.reform.divorce.caseformatterservice;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.reform.divorce.caseformatterservice.category.SmokeTest;

@ExtendWith(SpringExtension.class)
@Category(SmokeTest.class)
@SpringBootTest
public class CaseFormatterServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationArguments;

    @Test
    public void contextLoads() {
        applicationArguments.getStartupDate();
    }

}
