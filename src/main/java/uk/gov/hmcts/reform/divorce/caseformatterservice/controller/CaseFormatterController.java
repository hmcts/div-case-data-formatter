package uk.gov.hmcts.reform.divorce.caseformatterservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.caseformatterservice.domain.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

@RestController
@RequestMapping(path = "caseformatter/version/1", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE )
@Api(value = "Case Formatter Services", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CaseFormatterController {

    @Autowired
    private CaseFormatterService caseFormatterService;

    @PostMapping(path = "/to-ccd-format")
    @ApiOperation(value = "Given a case in Divorce format, will transform it into CCD format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into CCD format", response = CoreCaseData.class),
        }
    )
    public ResponseEntity<CoreCaseData> transformToCCDFormat(
        @RequestBody @ApiParam(value = "Divorce Session Data", required = true) DivorceSession data,
        @RequestHeader("Authorization")
        @ApiParam(value = "JWT authorisation token issued by IDAM", required = true) final String jwt) {
        return ResponseEntity.ok(caseFormatterService.transformToCCDFormat(data, jwt));
    }

    @PostMapping(path = "/to-divorce-format")
    @ApiOperation(value = "Given a case in CCD format, will transform it into Divorce session format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case transformed into Divorce Session format", response = DivorceSession.class),
        }
    )
    public ResponseEntity<DivorceSession> transformToDivorceFormat(
        @RequestBody @ApiParam(value = "CCD Data", required = true) CoreCaseData data) {
        return ResponseEntity.ok(caseFormatterService.transformToDivorceSession(data));
    }
}
