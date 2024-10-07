package com.book.review.service.integration;

import com.book.review.service.MockData;
import com.book.review.service.dao.repository.ReviewRepository;
import com.book.review.service.dao.repository.UserRepository;
import com.book.review.service.integration.config.TestContainerManager;
import com.book.review.service.integration.config.WireMockConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.StreamUtils.copyToString;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(classes = WireMockConfig.class, initializers = TestContainerManager.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/initial-data.sql")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected UserRepository userRepository;

    @Qualifier("wireMockGoogleBookService")
    @Autowired
    protected WireMockServer wireMockGoogleBookService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static String getResponseValue(String filePath) {
        return getJsonValue("responses/" + filePath);
    }

    protected static String getRequestValue(String filePath) {
        return getJsonValue("requests/" + filePath);
    }

    public static String getJsonValue(String jsonPath) {
        try {
            return copyToString(
                    MockData.class.getClassLoader().getResourceAsStream(jsonPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
