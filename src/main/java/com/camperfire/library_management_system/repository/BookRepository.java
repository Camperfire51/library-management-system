package com.camperfire.library_management_system.repository;

import com.camperfire.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsAvailableTrue(); // Custom method to find available books
}
