package com.book.review.service.controller;

import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Retrieve reviews for a book",
            description = "Returns a paginated list of reviews for the specified book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews successfully retrieved",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReviewResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    Page<ReviewResponseDto> getReviewsByBookId(@RequestParam String bookId,
                                               @RequestParam(defaultValue = "0") int pageNo,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return reviewService.getReviewsByBookId(bookId, pageable);
    }

    @Operation(summary = "Retrieve reviews by reviewer ID",
            description = "Returns a paginated list of reviews created by the specified reviewer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews successfully retrieved",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReviewResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    @GetMapping("/reviewers/{reviewerId}/reviews")
    Page<ReviewResponseDto> getReviewsByReviewerId(@PathVariable Long reviewerId,
                                                   @RequestParam(defaultValue = "0") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return reviewService.getReviewsByReviewerId(reviewerId, pageable);
    }

    @Operation(summary = "Add a new review",
            description = "Adds a new review for a book. Available to users with roles ADMIN or REVIEWER.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    public void addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.addReview(reviewRequestDto);
    }

    @Operation(summary = "Update a review",
            description = "Updates an existing review. Available to users with roles ADMIN or REVIEWER.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    public void updateReview(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.updateReview(id, reviewRequestDto);
    }

    @Operation(summary = "Delete a review",
            description = "Deletes a review by its ID. Available to users with roles ADMIN or REVIEWER.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    public void deleteReview(@PathVariable Long id, @RequestParam String bookId) {
        reviewService.deleteReview(id, bookId);
    }
}
