package com.book.review.service.mapper;


import com.book.review.service.dao.entity.ReviewEntity;
import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.model.ReviewerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "reviewerDto", target = "reviewer")
    @Mapping(source = "reviewEntity.id", target = "id")
    ReviewResponseDto toReviewDto(ReviewEntity reviewEntity, ReviewerDto reviewerDto);

    ReviewEntity toUpdateReviewEntity(ReviewRequestDto reviewRequestDto, @MappingTarget ReviewEntity reviewEntity);
}
