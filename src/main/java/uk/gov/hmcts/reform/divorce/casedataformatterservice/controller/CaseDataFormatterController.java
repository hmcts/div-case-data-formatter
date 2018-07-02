package uk.gov.hmcts.reform.divorce.casedataformatterservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.divorce.casedataformatterservice.service.CaseDataFormatterService;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(path = "casedataformatter")
@Api(value = "Case Data Formatter Services", consumes = "application/json", produces = "application/json")
public class CaseDataFormatterController {
    @Autowired
    private CaseDataFormatterService caseDataFormatterService;


    @PostMapping(path = "/version/1/format", consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Submits a divorce session to CCD")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Case Data was submitted to CCD. The body payload returns the complete "
            + "case back", response = CaseDetails.class),
        }
    )
    public ResponseEntity<CaseDetails> formatCaseData(
        @RequestBody @ApiParam(value = "Case Data", required = true) Object data,
        @RequestHeader("Authorization")
        @ApiParam(value = "JWT authorisation token issued by IDAM", required = true) final String jwt) {
        return ResponseEntity.ok(caseDataFormatterService.format(data, jwt));
    }


}
