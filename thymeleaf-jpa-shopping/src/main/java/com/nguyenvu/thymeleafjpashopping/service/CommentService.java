package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.CommentDTO;
import com.nguyenvu.thymeleafjpashopping.model.Comment;
import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.repository.CommentRepository;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CommentDTO> getCommentsByProductId(Long productId) {
        return commentRepository.findByProductProductIdOrderByCommentDateDesc(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CommentDTO> getCommentsByCustomerId(Long customerId) {
        return commentRepository.findByCustomerCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return convertToDTO(comment);
    }
    
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Product product = productRepository.findById(commentDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + commentDTO.getProductId()));
        
        Customer customer = customerRepository.findById(commentDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + commentDTO.getCustomerId()));
        
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setCommentDate(new Date());
        comment.setRating(commentDTO.getRating() != null ? commentDTO.getRating() : 5);
        comment.setProduct(product);
        comment.setCustomer(customer);
        
        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }
    
    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        
        comment.setText(commentDTO.getText());
        if (commentDTO.getRating() != null) {
            comment.setRating(commentDTO.getRating());
        }
        
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }
    
    public Double getAverageRatingForProduct(Long productId) {
        List<Comment> comments = commentRepository.findByProductProductId(productId);
        if (comments.isEmpty()) {
            return 0.0;
        }
        return comments.stream()
                .mapToInt(Comment::getRating)
                .average()
                .orElse(0.0);
    }
    
    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .text(comment.getText())
                .commentDate(comment.getCommentDate())
                .rating(comment.getRating())
                .productId(comment.getProduct().getProductId())
                .productName(comment.getProduct().getName())
                .customerId(comment.getCustomer().getCustomerId())
                .customerName(comment.getCustomer().getName())
                .customerUsername(comment.getCustomer().getUsername())
                .build();
    }
}

