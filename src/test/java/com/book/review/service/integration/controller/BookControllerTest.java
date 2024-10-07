package com.book.review.service.integration.controller;

import com.book.review.service.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.book.review.service.MockData.BASE_BOOKS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class BookControllerTest extends AbstractIntegrationTest {


    @Test
    void getAllBooksByFilter() throws Exception {
        // given
        final var expectedResponse = getResponseValue("books/GET-all-books-response.json");

        // when
        final var result = mockMvc.perform(get(BASE_BOOKS_URL + "?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestValue("books/GET-all-books-request.json"))).andReturn();

        // then
        final var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), true);
    }
}