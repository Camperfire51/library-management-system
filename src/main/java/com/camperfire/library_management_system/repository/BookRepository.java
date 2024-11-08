package com.camperfire.library_management_system.repository;

import com.camperfire.library_management_system.entity.Author;
import com.camperfire.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author authorId);

}
