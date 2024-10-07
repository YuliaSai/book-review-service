package com.book.review.service.dao.repository;

import com.book.review.service.dao.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByBookId(String bookId);

    Page<ReviewEntity> getReviewsByBookId(String bookId, Pageable pageable);

    Page<ReviewEntity> getReviewsByReviewerId(Long reviewerId, Pageable pageable);

    Optional<ReviewEntity> getReviewByIdAndBookIdAndReviewerId(Long id, String bookId, Long reviewerId);
}
