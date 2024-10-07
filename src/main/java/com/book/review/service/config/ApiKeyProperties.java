package com.book.review.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "google.api")
public class ApiKeyProperties {
    private String key;
}
