package uk.gov.hmcts.reform.divorce.caseformatterservice.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DocumentUrlRewrite {

    private static final String URL_REGEX = ".*?(/documents/.*)";


    private final String documentManagementStoreUrl;

    private final Pattern urlPatter; // Document pattern

    public DocumentUrlRewrite(@Value("${document.management.store.url}") String documentManagementStoreUrl) {
        this.documentManagementStoreUrl = documentManagementStoreUrl;
        this.urlPatter = Pattern.compile(documentManagementStoreUrl + "/documents/(.+)");
    }

    public String getDocumentUrl(String url) {
        if (!url.startsWith(documentManagementStoreUrl)) {
            return url.replaceAll(URL_REGEX, documentManagementStoreUrl + "$1");
        }

        return url;
    }

    public Optional<String> getDocumentId(String docUrl) {
        Matcher urlMatcher = urlPatter.matcher(docUrl);
        if (urlMatcher.find( )) {
            return Optional.ofNullable(urlMatcher.group(1));
        }

        return Optional.empty();
    }
}
