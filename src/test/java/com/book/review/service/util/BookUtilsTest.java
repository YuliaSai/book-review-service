package com.book.review.service.util;

import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.google.GoogleIndustryIdentifierDto;
import com.book.review.service.model.google.GoogleVolumeInfoDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.book.review.service.MockData.getValidGoogleBookItemDto;
import static com.book.review.service.MockData.getValidGoogleVolumeInfoDto;
import static org.assertj.core.api.Assertions.assertThat;

class BookUtilsTest {

    @Test
    void whenBuildQuery_shouldConstructCorrectQuery() {
        //
        final var filters = RequestFilterDto.builder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9781471173943")
                .build();

        // when
        final var query = BookUtils.buildQuery(filters);

        // then
        assertThat(query).isEqualTo("intitle:The Great Gatsby+inauthor:F. Scott Fitzgerald+isbn:9781471173943");
    }

    @Test
    void whenBuildQuery_shouldIgnoreEmptyFields() {
        // given
        final var filters = RequestFilterDto.builder().title("The Great Gatsby").build();

        // when
        final var query = BookUtils.buildQuery(filters);

        // then
        assertThat(query).isEqualTo("intitle:The Great Gatsby");
    }

    @Test
    void whenBuildQuery_shouldReturnEmptyQueryForEmptyFilter() {
        // Given
        final var filters = RequestFilterDto.builder().build();

        // When
        final var query = BookUtils.buildQuery(filters);

        // Then
        assertThat(query).isEmpty();
    }

    @Test
    void whenConvertToBookResponseDto_shouldConvertGoogleBookItemDtoToBookResponseDto() {
        // given
        final var googleBookItem = getValidGoogleBookItemDto();

        // when
        final var bookResponseDto = BookUtils.convertToBookResponseDto(googleBookItem);

        // then
        assertThat(bookResponseDto).isNotNull();
        assertThat(bookResponseDto.id()).isEqualTo("iWA-DwAAQBAJ");
        assertThat(bookResponseDto.title()).isEqualTo("The Great Gatsby");
        assertThat(bookResponseDto.author()).isEqualTo("F. Scott Fitzgerald");
        assertThat(bookResponseDto.publisher()).isEqualTo("Scribner");
        assertThat(bookResponseDto.publishedDate()).isEqualTo("2005-11-15");
        assertThat(bookResponseDto.description()).isEqualTo("Some text");
        assertThat(bookResponseDto.isbn()).isEqualTo("9780743273565");
    }

    @Test
    void whenGetIsbn_shouldReturnIsbn13IfAvailable() {
        // given
        final var volumeInfo = getValidGoogleVolumeInfoDto();

        // when
        final var isbn = BookUtils.getIsbn(volumeInfo);

        // then
        assertThat(isbn).isEqualTo("9780743273565");
    }

    @Test
    void whenGetIsbn_shouldReturnNullIfIsbn13IsNotAvailable() {
        // given
        final var volumeInfo = GoogleVolumeInfoDto.builder()
                .industryIdentifiers(List.of(
                        GoogleIndustryIdentifierDto.builder()
                                .type("ISBN_10")
                                .identifier("0134685997")
                                .build()))
                .build();

        // when
        final var isbn = BookUtils.getIsbn(volumeInfo);

        // then
        assertThat(isbn).isNull();
    }

    @Test
    void whenGetIsbn_shouldReturnNullIfNoIndustryIdentifiers() {
        // given
        final var volumeInfo = GoogleVolumeInfoDto.builder()
                .industryIdentifiers(List.of(
                        GoogleIndustryIdentifierDto.builder().build()))
                .build();

        // when
        final var isbn = BookUtils.getIsbn(volumeInfo);

        // then
        assertThat(isbn).isNull();
    }

}