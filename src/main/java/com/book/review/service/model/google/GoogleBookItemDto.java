package com.book.review.service.model.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookItemDto {
    private String id;
    private String kind;
    private String etag;
    private String selfLink;
    private GoogleVolumeInfoDto volumeInfo;
}
