package com.camperfire.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String isbn;

    @NonNull
    private int publicationYear;

    private boolean isAvailable = true; // Consider defaulting to true for available books

    @NonNull
    @ManyToOne
    private Author author;

    @ManyToOne
    private Member borrowedBy;

    public void setBorrowedBy(Member member) {
        this.borrowedBy = member;
        this.isAvailable = (member == null); // Update availability based on whether it's borrowed
    }
}
