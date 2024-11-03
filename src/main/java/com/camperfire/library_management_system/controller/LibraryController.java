package com.camperfire.library_management_system.controller;

import com.camperfire.library_management_system.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        return ResponseEntity.ok(libraryService.borrowBook(memberId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        return ResponseEntity.ok(libraryService.returnBook(memberId, bookId));
    }
}

