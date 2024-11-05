package com.camperfire.library_management_system.service;

import com.camperfire.library_management_system.entity.Author;
import com.camperfire.library_management_system.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Create or Update Author
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Read All Authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Read Author by ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    // Delete Author by ID
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
