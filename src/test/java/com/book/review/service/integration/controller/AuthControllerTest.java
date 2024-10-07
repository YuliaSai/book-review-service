package com.book.review.service.integration.controller;

import com.book.review.service.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.book.review.service.MockData.AUTH_REGISTER_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class AuthControllerTest extends AbstractIntegrationTest {

    @Test
    void registerUser() throws Exception {
        // given
        reviewRepository.deleteAll();
        userRepository.deleteAll();

        // when
        final var result = mockMvc.perform(post(AUTH_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestValue("users/POST-register-user-request.json"))).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}