package uk.gov.hmcts.reform.divorce.caseformatterservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.DnRefusalCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.documentupdate.DocumentUpdateRequest;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

import java.util.Map;

@RestController
@RequestMapping(path = "caseformatter/version/1", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Case Formatter Services", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CaseFormatterController {

    @Autowired
    private CaseFormatterService caseFormatterService;

    @PostMapping(path = "/to-ccd-format")
    @ApiOperation(value = "Given a case in Divorce format, will transform it into CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into CCD format", response = CoreCaseData.class),
    })
    public ResponseEntity<CoreCaseData> transformToCCDFormat(
        @RequestBody @ApiParam(value = "Divorce Session Data", required = true) DivorceSession data,
        @RequestHeader("Authorization")
        @ApiParam(value = "JWT authorisation token issued by IDAM", required = true) final String jwt) {
        return ResponseEntity.ok(caseFormatterService.transformToCCDFormat(data, jwt));
    }

    @PostMapping(path = "/to-divorce-format")
    @ApiOperation(value = "Given a case in CCD format, will transform it into Divorce session format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into Divorce Session format",
            response = DivorceSession.class),
    })
    public ResponseEntity<DivorceSession> transformToDivorceFormat(
        @RequestBody @ApiParam(value = "CCD Data", required = true) CoreCaseData data) {
        return ResponseEntity.ok(caseFormatterService.transformToDivorceSession(data));
    }

    /**
     * Adds new documents to case data.
     *
     * @deprecated This functionality was moved to COS. Please refrain from using this endpoint.
     */
    @PostMapping(path = "/add-documents")
    @ApiOperation(value = "Given a case in CCD format and documents to add this will update the case with documents")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "D8DocumentsGenerated values after CCD update",
            response = Map.class),
    })
    @Deprecated
    public ResponseEntity<Map<String, Object>> addDocuments(
        @RequestBody @ApiParam(value = "CCD Data", required = true) DocumentUpdateRequest documentUpdateRequest) {

        log.warn("/add-documents endpoint was called.");

        return ResponseEntity.ok(caseFormatterService.addDocuments(documentUpdateRequest.getCaseData(),
            documentUpdateRequest.getDocuments()));
    }

    @PostMapping(path = "/remove-all-petition-documents")
    @ApiOperation(value = "Given a case in CCD format removes all petitions from generated documents collection")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "D8DocumentsGenerated values after CCD update", response = Map.class)})
    public ResponseEntity<Map<String, Object>> removeAllPetitions(
        @RequestBody @ApiParam(value = "CCD Data", required = true) Map<String, Object> caseData) {
        return ResponseEntity.ok(caseFormatterService.removeAllPetitionDocuments(caseData));
    }

    @PostMapping(path = "/remove/documents/{documentType}")
    @ApiOperation(value = "Given a case in CCD format removes all document of indicated type from generated documents collection")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "D8DocumentsGenerated values after CCD update", response = Map.class)})
    public ResponseEntity<Map<String, Object>> removeDocumentByType(
        @RequestBody @ApiParam(value = "CCD Data", required = true) Map<String, Object> caseData,
        @PathVariable String documentType) {
        return ResponseEntity.ok(caseFormatterService.removeAllDocumentsByType(caseData, documentType));
    }

    @PostMapping(path = "/to-aos-submit-format")
    @ApiOperation(value = "Given a case in Divorce format, will extract the AOS data and convert it to CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into AOS format", response = AosCaseData.class),
    })
    public ResponseEntity<AosCaseData> getAosCaseData(
        @RequestBody @ApiParam(value = "Divorce Session Data", required = true) DivorceSession data) {
        return ResponseEntity.ok(caseFormatterService.getAosCaseData(data));
    }

    @PostMapping(path = "/to-dn-submit-format")
    @ApiOperation(value = "Given a case in Divorce format, will extract the DN data and convert it to CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into DN format", response = DnCaseData.class),
    })
    public ResponseEntity<DnCaseData> getDnCaseData(
        @RequestBody @ApiParam(value = "Divorce Session Data", required = true) DivorceSession divorceSession) {
        return ResponseEntity.ok(caseFormatterService.getDnCaseData(divorceSession));
    }

    @PostMapping(path = "/to-dn-clarification-format")
    @ApiOperation(value = "Given a case in Divorce format, will extract the DN Clarification data and convert it to CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into DN Clarification format", response = DnCaseData.class),
    })
    public ResponseEntity<DnRefusalCaseData> getDnClarificationCaseData(
        @RequestBody @ApiParam(value = "Divorce CCD and Session data", required = true) DivorceCaseWrapper divorceCaseWrapper) {
        return ResponseEntity.ok(caseFormatterService.getDnClarificationCaseData(divorceCaseWrapper));
    }

    @PostMapping(path = "/to-da-submit-format")
    @ApiOperation(value = "Given a case in Divorce format, will extract the DA data and convert it to CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into DA format", response = DaCaseData.class),
    })
    public ResponseEntity<DaCaseData> getDaCaseData(
        @RequestBody @ApiParam(value = "Divorce Session Data", required = true) DivorceSession divorceSession) {
        return ResponseEntity.ok(caseFormatterService.getDaCaseData(divorceSession));
    }
}
