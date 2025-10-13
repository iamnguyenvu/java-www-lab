package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CommentDTO;
import com.nguyenvu.thymeleafjpashopping.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getId())
                .text(comment.getText())
                .productId(comment.getProduct().getId())
                .productName(comment.getProduct().getName())
                .build();
    }
}
