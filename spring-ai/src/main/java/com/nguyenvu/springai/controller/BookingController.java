package com.nguyenvu.springai.controller;

import com.nguyenvu.springai.model.BookingDetails;
import com.nguyenvu.springai.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingDetails>> getAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetails> getById(@PathVariable String id) {
        BookingDetails bookingDetails = bookingService.findById(id);
        return bookingDetails != null ? ResponseEntity.ok(bookingDetails) : ResponseEntity.notFound().build();
    }
}
