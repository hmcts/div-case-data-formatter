package uk.gov.hmcts.reform.divorce;

import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.util.Base64;

class IdamUtils {

    @Value("${idam.api.baseurl}")
    private String idamUserBaseUrl;

    @Value("${auth.idam.client.redirectUri}")
    private String idamRedirectUri;

    @Value("${auth.idam.client.secret}")
    private String idamSecret;

    void createUserInIdam(String username, String password) {
        String payload = "{\"email\":\"" + username + "@example.com\","
            + "\"forename\":\"Test\",\"surname\":\"User\",\"password\":\"" + password + "\"}";

        SerenityRest.given()
            .header("Content-Type", "application/json")
            .body(payload)
            .post(idamCreateUrl());
    }

    private String idamCreateUrl() {
        return idamUserBaseUrl + "/testing-support/accounts";
    }

    String generateUserTokenWithNoRoles(String username, String password) {
        String userLoginDetails = String.join(":", username + "@test.com", password);
        final String authHeader = "Basic " + new String(Base64.getEncoder().encode(userLoginDetails.getBytes()));

        final String code = SerenityRest.given()
                .header("Authorization", authHeader)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .post(idamCodeUrl())
                .body()
                .path("code");

        final String token = SerenityRest.given()
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .post(idamTokenUrl(code))
                .body()
                .path("access_token");

        return "Bearer " + token;
    }

    private String idamCodeUrl() {
        return idamUserBaseUrl + "/oauth2/authorize"
            + "?response_type=code"
            + "&client_id=divorce"
            + "&redirect_uri=" + idamRedirectUri;
    }

    private String idamTokenUrl(String code) {
        return idamUserBaseUrl + "/oauth2/token"
            + "?code=" + code
            + "&client_id=divorce"
            + "&client_secret=" + idamSecret
            + "&redirect_uri=" + idamRedirectUri
            + "&grant_type=authorization_code";
    }
}
