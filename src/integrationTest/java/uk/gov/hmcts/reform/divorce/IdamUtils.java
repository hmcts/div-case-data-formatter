package uk.gov.hmcts.reform.divorce;

import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

class IdamUtils {

    @Value("${auth.idam.client.baseUrl}")
    private String idamUserBaseUrl;

    void createUserInIdam(String username, String password) {
        String payload = "{\"email\":\"" + username + "@test.com\", \"forename\":\"" + username
            + "\",\"surname\":\"User\",\"password\":\"" + password + "\"}";

        SerenityRest.given()
            .header("Content-Type", "application/json")
            .body(payload)
            .post(idamCreateUrl());
    }

    private String idamCreateUrl() {
        return idamUserBaseUrl + "/testing-support/accounts";
    }

    private String loginUrl() {
        return idamUserBaseUrl + "/oauth2/authorize?response_type=token&client_id=divorce&redirect_uri="
                            + "https://www.preprod.ccd.reform.hmcts.net/oauth2redirect";
    }

    String generateUserTokenWithNoRoles(String username, String password) {
        String userLoginDetails = String.join(":", username + "@test.com", password);
        final String authHeader = "Basic " + new String(Base64.getEncoder().encode(userLoginDetails.getBytes()));

        final String token = SerenityRest.given()
                .header("Authorization", authHeader)
                .post(loginUrl())
                .body()
                .path("access-token");

        return "Bearer " + token;
    }
}
