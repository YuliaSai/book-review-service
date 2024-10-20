package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "Book response data transfer object")
@Builder(toBuilder = true)
public record BookResponseDto(

        @Schema(description = "Unique identifier of the book", example = "_ojXNuzgHRcC")
        String id,
        @Schema(description = "Title of the book", example = "The Great Gatsby")
        String title,
        @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
        String author,
        @Schema(description = "ISBN number of the book", example = "9780451524935")
        String isbn,
        @Schema(description = "Publisher of the book", example = "Little, Brown and Company")
        String publisher,
        @Schema(description = "Published date of the book", example = "1951-07-16")
        String publishedDate,
        @Schema(description = "Description of the book", example = "A novel about teenage rebellion.")
        String description,
        @Schema(description = "List of reviews for the book",
                type = "array",
                implementation = ReviewResponseDto.class)
        List<ReviewResponseDto> reviews
) {

}
