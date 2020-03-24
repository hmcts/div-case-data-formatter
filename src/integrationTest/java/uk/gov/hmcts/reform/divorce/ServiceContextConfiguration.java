package uk.gov.hmcts.reform.divorce;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@Lazy
@Configuration
@ContextConfiguration(classes = {ServiceContextConfiguration.class})
@PropertySource({"classpath:application.properties"})
@PropertySource({"classpath:application-${env}.properties"})
public class ServiceContextConfiguration {
}
