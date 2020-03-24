package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ccdtodivorceformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.caseformatterservice.CaseFormatterServiceApplication;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.UploadedFile;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseFormatterServiceApplication.class)
public class DocumentsMappingUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldHandleDocumentLinkBeingNull()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/document-link-null.json", CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        List<UploadedFile> marriageCertificateFiles = actualDivorceSession.getMarriageCertificateFiles();
        assertEquals(marriageCertificateFiles.size(), 1);
        assertEquals(marriageCertificateFiles.get(0).getFileName(), "some-file.pdf");
    }

    @Test
    public void shouldHandleDocumentDateBeingEmptyString()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/document-dates-empty.json",
                CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        List<UploadedFile> marriageCertificateFiles = actualDivorceSession.getMarriageCertificateFiles();

        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String date = dateFormat.format(marriageCertificateFiles.get(0).getCreatedOn());

        assertEquals(marriageCertificateFiles.size(), 3);
        assertEquals(marriageCertificateFiles.get(0).getFileName(), "some-file.pdf");
        assertEquals(date, "2019-05-10");
    }
}
