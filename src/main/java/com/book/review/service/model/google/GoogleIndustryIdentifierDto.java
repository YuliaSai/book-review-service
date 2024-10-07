package com.book.review.service.model.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIndustryIdentifierDto {
    private String type;
    private String identifier;
}
