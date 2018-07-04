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
import uk.gov.hmcts.reform.divorce.caseformatterservice.service.CaseFormatterService;

@RestController
@RequestMapping(path = "caseformatter", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE )
@Api(value = "Case Formatter Services", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CaseFormatterController {

    @Autowired
    private CaseFormatterService caseFormatterService;

    @PostMapping(path = "/version/1/format")
    @ApiOperation(value = "Submits a divorce session to CCD")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case Data was submitted to CCD. The body payload returns the complete "
            + "case back", response = Object.class),
        }
    )
    //TODO:Fix
    public ResponseEntity<Object> formatCase(
        @RequestBody @ApiParam(value = "FE", required = true) Object data,
        @RequestHeader("Authorization")
        @ApiParam(value = "JWT authorisation token issued by IDAM", required = true) final String jwt) {
        return ResponseEntity.ok(caseFormatterService.format(data, jwt));
    }
}
