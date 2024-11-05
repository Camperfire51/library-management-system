package com.camperfire.library_management_system.service;

import com.camperfire.library_management_system.entity.Author;
import com.camperfire.library_management_system.entity.Book;
import com.camperfire.library_management_system.entity.Member;
import com.camperfire.library_management_system.repository.AuthorRepository;
import com.camperfire.library_management_system.repository.BookRepository;
import com.camperfire.library_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
    }

    // Create or Update Book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Read All Books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Read Book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // Delete Book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Additional Operations

    // Find Books by Author
    public List<Book> findBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);

        return bookRepository.findByAuthor(author);
    }

    // Check Availability of a Book
    public boolean isBookAvailable(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        return book != null && book.getBorrowedBy() == null;
    }

    @Transactional
    // Borrowing a book
    public Book borrowBookById(Long memberId, Long bookId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return null;
        }

        Book book = bookOpt.get();
        if (book.getBorrowedBy() != null) {
            return null;
        }

        Member member = memberOpt.get();
        book.setBorrowedBy(member); // Set the member who borrowed the book
        member.getBorrowedBooks().add(book); // Add book to member's borrowed books list

        bookRepository.save(book); // Save updated book
        memberRepository.save(member); // Save updated member

        return book;
    }

    @Transactional
    // Returning a book
    public void returnBookById(Long memberId, Long bookId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        System.out.println("method test");

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return;
        }

        Book book = bookOpt.get();
        Member member = memberOpt.get();

        System.out.println("Current Borrower: " + book.getBorrowedBy());
        System.out.println("Attempting to return by Member: " + member);

        // Check if the book is borrowed by this member
        if (!book.getBorrowedBy().equals(member)) {
            return;
        }

        // Update the book and member records
        book.setBorrowedBy(null); // Remove the borrowed member reference
        member.getBorrowedBooks().remove(book); // Remove the book from the member's borrowed list

        bookRepository.save(book); // Save updated book
        memberRepository.save(member); // Save updated member

        // Flush the changes to ensure they’re persisted to the database
        bookRepository.flush();
        memberRepository.flush();

        System.out.println("Updating book's borrowedBy to null");

    }
}
