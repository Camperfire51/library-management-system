package com.camperfire.library_management_system.controller;

import com.camperfire.library_management_system.entity.Book;
import com.camperfire.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Additional endpoints
    @GetMapping("/read/{id}")
    public ResponseEntity<String> readBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id).getContent());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.findBooksByAuthor(authorId));
    }

    @GetMapping("/availability/{id}")
    public ResponseEntity<Boolean> checkBookAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.isBookAvailable(id));
    }

    @GetMapping("/borrow/{id}")
    public ResponseEntity<Book> borrowBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.borrowBookById(1L,id));
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<String> returnBookById(@PathVariable Long id) {

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Long memberId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        bookService.returnBookById(1L,id);
        return ResponseEntity.noContent().build();
    }
}
