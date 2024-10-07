package com.book.review.service.integration.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@ActiveProfiles("test")
@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockGoogleBookService() {
        return new WireMockServer(options().port(8181).usingFilesUnderClasspath("wiremock/google-book"));
    }
}
