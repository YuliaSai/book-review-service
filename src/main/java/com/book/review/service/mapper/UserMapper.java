package com.book.review.service.mapper;

import com.book.review.service.dao.entity.UserEntity;
import com.book.review.service.model.ReviewerDto;
import com.book.review.service.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReviewerDto toReviewDto(UserEntity userEntity);

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(UserDto userDto);
}
