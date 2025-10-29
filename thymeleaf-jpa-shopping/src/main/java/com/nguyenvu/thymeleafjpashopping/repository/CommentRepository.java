package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductProductId(Long productId);
    List<Comment> findByCustomerCustomerId(Long customerId);
    List<Comment> findByProductProductIdOrderByCommentDateDesc(Long productId);
    List<Comment> findByRating(Integer rating);
    List<Comment> findByRatingGreaterThanEqual(Integer rating);
}
