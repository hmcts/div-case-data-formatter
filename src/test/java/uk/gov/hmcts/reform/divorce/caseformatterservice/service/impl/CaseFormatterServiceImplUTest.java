package uk.gov.hmcts.reform.divorce.caseformatterservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.DocumentUpdateRequest;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.mapper.DocumentCollectionDocumentRequestMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaseFormatterServiceImplUTest {
    private static final String D8_DOCUMENTS_GENERATED_CCD_FIELD = "D8DocumentsGenerated";
    private static final String HAL_BINARY_RESPONSE_CONTEXT_PATH =
        (String)ReflectionTestUtils.getField(
            DocumentCollectionDocumentRequestMapper.class, "HAL_BINARY_RESPONSE_CONTEXT_PATH");
    private static final String PDF_FILE_EXTENSION =
        (String)ReflectionTestUtils.getField(
            DocumentCollectionDocumentRequestMapper.class, "PDF_FILE_EXTENSION");

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Mock
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @Mock
    private DocumentCollectionDocumentRequestMapper documentCollectionDocumentRequestMapper;

    @Mock
    private DivorceCaseToAosCaseMapper divorceCaseToAosCaseMapper;

    @Mock
    private DivorceCaseToDnCaseMapper divorceCaseToDnCaseMapper;

    @InjectMocks
    private CaseFormatterServiceImpl classUnderTest;

    @Test
    public void whenTransformToCCDFormat_thenProceedAsExpected() {
        final String userToken = "someToken";
        final DivorceSession divorceSession = new DivorceSession();

        final CoreCaseData expectedCaseData = new CoreCaseData();

        when(divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession))
            .thenReturn(expectedCaseData);

        final CoreCaseData actualCaseData = classUnderTest.transformToCCDFormat(divorceSession, userToken);

        assertEquals(expectedCaseData, actualCaseData);

        verify(divorceCaseToCCDMapper).divorceCaseDataToCourtCaseData(divorceSession);
    }

    @Test
    public void whenTransformToDivorceSession_thenProceedAsExpected() {
        final CoreCaseData coreCaseData = new CoreCaseData();
        final DivorceSession expectedDivorceSession = new DivorceSession();

        when(ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData)).thenReturn(expectedDivorceSession);

        final DivorceSession actualDivorceSession = classUnderTest.transformToDivorceSession(coreCaseData);

        assertEquals(expectedDivorceSession, actualDivorceSession);

        verify(ccdCaseToDivorceMapper).courtCaseDataToDivorceCaseData(coreCaseData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenCoreCaseDataIsNull_whenAddDocuments_thenReturnThrowException() {
        classUnderTest.addDocuments(null,
            Collections.singletonList(GeneratedDocumentInfo.builder().build()));
    }

    @Test
    public void givenGeneratedDocumentInfoIsNull_whenAddDocuments_thenReturnSameCaseData() {
        final Map<String, Object> expected = Collections.emptyMap();
        final Map<String, Object> input = Collections.emptyMap();

        Map<String, Object> actual = classUnderTest.addDocuments(input, null);

        assertEquals(expected, actual);
    }

    @Test
    public void givenGeneratedDocumentInfoIsEmpty_whenAddDocuments_thenReturnSameCaseData() {
        final Map<String, Object> expected = Collections.emptyMap();
        final Map<String, Object> input = Collections.emptyMap();

        Map<String, Object> actual = classUnderTest.addDocuments(input, Collections.emptyList());

        assertEquals(expected, actual);
    }

    @Test
    public void givenNoD8DocumentsInCaseData_whenAddDocuments_thenAddDocuments() {
        final String url1 = "url1";
        final String documentType1 = "petition";
        final String fileName1 = "fileName1";
        final String url2 = "url1";
        final String documentType2 = "aos";
        final String fileName2 = "fileName2";

        final GeneratedDocumentInfo generatedDocumentInfo1 = createGeneratedDocument(url1, documentType1, fileName1);
        final GeneratedDocumentInfo generatedDocumentInfo2 = createGeneratedDocument(url2, documentType2, fileName2);

        final CollectionMember<Document> document1 = createCollectionMemberDocument(url1, documentType1, fileName1);
        final CollectionMember<Document> document2 = createCollectionMemberDocument(url2, documentType2, fileName2);

        when(documentCollectionDocumentRequestMapper.map(generatedDocumentInfo1)).thenReturn(document1);
        when(documentCollectionDocumentRequestMapper.map(generatedDocumentInfo2)).thenReturn(document2);

        final Map<String, Object> expected =
            Collections.singletonMap(D8_DOCUMENTS_GENERATED_CCD_FIELD, Arrays.asList(document1, document2));

        final Map<String, Object> input = new HashMap<>();

        Map<String, Object> actual = classUnderTest.addDocuments(input,
            Arrays.asList(generatedDocumentInfo1, generatedDocumentInfo2));

        assertEquals(expected, actual);
    }

    @Test
    public void givenConflictingD8DocumentsExistsInCaseData_whenAddDocuments_thenAddDocuments() {
        final String url1 = "url1";
        final String documentType1 = "petition";
        final String fileName1 = "fileName1";
        final String url2 = "url2";
        final String documentType2 = "aos";
        final String fileName2 = "fileName2";

        final String url3 = "url3";
        final String documentType3 = "aos1";
        final String fileName3 = "fileName3";

        final String url4 = "url4";
        final String documentType4 = "aos";
        final String fileName4 = "fileName4";

        final GeneratedDocumentInfo generatedDocumentInfo1 = createGeneratedDocument(url1, documentType1, fileName1);
        final GeneratedDocumentInfo generatedDocumentInfo2 = createGeneratedDocument(url2, documentType2, fileName2);

        final CollectionMember<Document> document1 = createCollectionMemberDocument(url1, documentType1, fileName1);
        final CollectionMember<Document> document2 = createCollectionMemberDocument(url2, documentType2, fileName2);
        final CollectionMember<Document> document3 = createCollectionMemberDocument(url3, documentType3, fileName3);
        final CollectionMember<Document> document4 = createCollectionMemberDocument(url4, documentType4, fileName4);

        when(documentCollectionDocumentRequestMapper.map(generatedDocumentInfo1)).thenReturn(document1);
        when(documentCollectionDocumentRequestMapper.map(generatedDocumentInfo2)).thenReturn(document2);

        final Map<String, Object> expected =
            Collections.singletonMap(D8_DOCUMENTS_GENERATED_CCD_FIELD, Arrays.asList(document3, document1, document2));

        final Map<String, Object> input = new HashMap<>();
        input.put(D8_DOCUMENTS_GENERATED_CCD_FIELD, Arrays.asList(document3, document4));

        DocumentUpdateRequest documentUpdateRequest = new DocumentUpdateRequest();
        documentUpdateRequest.setDocuments(Arrays.asList(generatedDocumentInfo1, generatedDocumentInfo2));
        documentUpdateRequest.setCaseData(input);

        Map<String, Object> actual = classUnderTest.addDocuments(input,
            Arrays.asList(generatedDocumentInfo1, generatedDocumentInfo2));

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAosCaseData_thenProceedAsExpected() {
        DivorceSession divorceSession = mock(DivorceSession.class);
        AosCaseData aosCaseData = mock(AosCaseData.class);

        when(divorceCaseToAosCaseMapper.divorceCaseDataToAosCaseData(divorceSession)).thenReturn(aosCaseData);

        assertEquals(aosCaseData, classUnderTest.getAosCaseData(divorceSession));

        verify(divorceCaseToAosCaseMapper).divorceCaseDataToAosCaseData(divorceSession);
    }

    @Test
    public void whenGetDnCaseData_thenProceedAsExpected() {
        DivorceSession divorceSession = mock(DivorceSession.class);
        DnCaseData dnCaseData = mock(DnCaseData.class);

        when(divorceCaseToDnCaseMapper.divorceCaseDataToDnCaseData(divorceSession)).thenReturn(dnCaseData);

        assertEquals(dnCaseData, classUnderTest.getDnCaseData(divorceSession));

        verify(divorceCaseToDnCaseMapper).divorceCaseDataToDnCaseData(divorceSession);
    }

    private GeneratedDocumentInfo createGeneratedDocument(String url, String documentType, String fileName) {
        return GeneratedDocumentInfo.builder()
            .url(url)
            .documentType(documentType)
            .fileName(fileName)
            .build();
    }

    private CollectionMember<Document> createCollectionMemberDocument(String url, String documentType,
                                                                      String fileName) {
        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        final Document document = new Document();
        final DocumentLink documentLink = new DocumentLink();

        documentLink.setDocumentUrl(url);
        documentLink.setDocumentBinaryUrl(url + HAL_BINARY_RESPONSE_CONTEXT_PATH);
        documentLink.setDocumentFilename(fileName + PDF_FILE_EXTENSION);
        document.setDocumentFileName(fileName);
        document.setDocumentLink(documentLink);
        document.setDocumentType(documentType);

        collectionMember.setValue(document);

        return collectionMember;
    }
}
