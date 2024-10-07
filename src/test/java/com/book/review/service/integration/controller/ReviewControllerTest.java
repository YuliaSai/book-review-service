package com.book.review.service.integration.controller;

import com.book.review.service.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.book.review.service.MockData.BASE_REVIEWS_URL;
import static com.book.review.service.MockData.REVIEWS_WITH_ID_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

class ReviewControllerTest extends AbstractIntegrationTest {

    @Test
    void getReviewsByBookId() throws Exception {
        // given
        final var expectedResponse = getResponseValue("reviews/GET-all-reviews-by-book-id-response.json");

        // when
        final var result = mockMvc.perform(get(BASE_REVIEWS_URL +
                "?bookId=iWA-DwAAQBAJ&page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), true);
    }

    @Test
    void getReviewsByReviewerId() throws Exception {
        // given
        final var expectedResponse = getResponseValue("reviews/GET-all-reviews-by-reviewer-id-response.json");

        // when
        final var result = mockMvc.perform(get(BASE_REVIEWS_URL + "/reviewers/1/reviews?&page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), true);
    }

    @Test
    @WithMockUser(username = "jane.smith@example.com", roles = {"REVIEWER"})
    void addReview() throws Exception {
        //given
        reviewRepository.deleteAll();

        // when
        final var result = mockMvc.perform(post(BASE_REVIEWS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestValue("reviews/POST-add-review-request.json"))).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = "jane.smith@example.com", roles = {"REVIEWER"})
    void updateReview() throws Exception {
        // when
        final var result = mockMvc.perform(put(REVIEWS_WITH_ID_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestValue("reviews/PUT-update-review-request.json"))).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = "jane.smith@example.com", roles = {"REVIEWER"})
    void deleteReview() throws Exception {
        // when
        final var result = mockMvc.perform(delete(REVIEWS_WITH_ID_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "iWA-DwAAQBAJ")).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}