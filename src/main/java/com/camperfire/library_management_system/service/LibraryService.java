package com.camperfire.library_management_system.service;

import com.camperfire.library_management_system.entity.Book;
import com.camperfire.library_management_system.entity.Member;
import com.camperfire.library_management_system.repository.AuthorRepository;
import com.camperfire.library_management_system.repository.BookRepository;
import com.camperfire.library_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, MemberRepository memberRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.authorRepository = authorRepository;
    }

    // Borrowing a book
    public String borrowBook(Long memberId, Long bookId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return "Member or Book not found.";
        }

        Book book = bookOpt.get();
        if (book.getBorrowedBy() != null) {
            return "Book is currently unavailable.";
        }

        Member member = memberOpt.get();
        book.setBorrowedBy(member); // Set the member who borrowed the book
        member.getBorrowedBooks().add(book); // Add book to member's borrowed books list

        bookRepository.save(book); // Save updated book
        memberRepository.save(member); // Save updated member

        return "Book borrowed successfully!";
    }

    // Returning a book
    public String returnBook(Long memberId, Long bookId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return "Member or Book not found.";
        }

        Book book = bookOpt.get();
        Member member = memberOpt.get();

        // Check if the book is borrowed by this member
        if (!book.getBorrowedBy().equals(member)) {
            return "This book was not borrowed by this member.";
        }

        // Update the book and member records
        book.setBorrowedBy(null); // Remove the borrowed member reference
        member.getBorrowedBooks().remove(book); // Remove the book from the member's borrowed list

        bookRepository.save(book); // Save updated book
        memberRepository.save(member); // Save updated member

        return "Book returned successfully!";
    }
}
