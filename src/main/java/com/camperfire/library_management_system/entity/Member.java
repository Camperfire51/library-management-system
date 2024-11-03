package com.camperfire.library_management_system.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @OneToMany(mappedBy = "borrowedBy")
    private List<Book> borrowedBooks = new ArrayList<>();

}