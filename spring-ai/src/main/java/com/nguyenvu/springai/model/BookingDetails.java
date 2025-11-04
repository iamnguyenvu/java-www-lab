package com.nguyenvu.springai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {
    private String number;
    private String firstName;
    private String lastName;
    private String from;
    private String to;
    private String date;
}
