package com.book.review.service.dao.repository;

import com.book.review.service.dao.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Page<ReviewEntity> getReviewsByBookId(String bookId, Pageable pageable);

    Page<ReviewEntity> getReviewsByReviewerId(Long reviewerId, Pageable pageable);

    Optional<ReviewEntity> getReviewByIdAndBookIdAndReviewerId(Long id, String bookId, Long reviewerId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.bookId IN :bookIds")
    List<ReviewEntity> findByBookIds(@Param("bookIds") List<String> bookIds);
}
