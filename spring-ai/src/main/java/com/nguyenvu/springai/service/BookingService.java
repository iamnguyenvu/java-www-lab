package com.nguyenvu.springai.service;

import com.nguyenvu.springai.model.BookDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final Map<String, BookDetails> bookings = Map.of(
            "ABC123", new BookDetails("ABC123", "John", "Doe", "New York", "Los Angeles", "2024-07-15"),
            "DEF456", new BookDetails("DEF456", "Jane", "Smith", "Chicago", "Miami", "2024-08-20"),
            "GHI789", new BookDetails("GHI789", "Alice", "Johnson", "San Francisco", "Seattle", "2024-09-10")
    );

    public List<BookDetails> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public BookDetails findById(String id) {
        return bookings.get(id);
    }
}
