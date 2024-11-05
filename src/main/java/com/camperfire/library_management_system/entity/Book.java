package com.camperfire.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
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

    @NonNull
    @ManyToOne
    @JsonBackReference
    private Author author;

    @ManyToOne
    @JoinColumn(name = "borrowed_by_id")
    @JsonManagedReference
    private Member borrowedBy;

    @NonNull
    private String content;
}
