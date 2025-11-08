package com.nguyenvu.springai.service;

import com.nguyenvu.springai.model.BookingDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {
    private final Map<String, BookingDetails> bookings = Map.of(
            "ABC123", new BookingDetails("ABC123", "John", "Doe", "New York", "Los Angeles", "2024-07-15"),
            "DEF456", new BookingDetails("DEF456", "Jane", "Smith", "Chicago", "Miami", "2024-08-20"),
            "GHI789", new BookingDetails("GHI789", "Alice", "Johnson", "San Francisco", "Seattle", "2024-09-10")
    );

    public List<BookingDetails> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public BookingDetails findById(String id) {
        return bookings.get(id);
    }
}
