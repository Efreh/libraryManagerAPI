package com.efr.libraryManagerAPI.service;

import com.efr.libraryManagerAPI.exception.BookNotFoundException;
import com.efr.libraryManagerAPI.model.Book;
import com.efr.libraryManagerAPI.repository.BookRepository;
import com.efr.libraryManagerAPI.service.interfaces.BookServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService implements BookServiceInterface {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthors(bookDetails.getAuthors());
            book.setGenre(bookDetails.getGenre());
            book.setPrice(bookDetails.getPrice());
            book.setPages(bookDetails.getPages());
            book.setTitle(bookDetails.getTitle());
            book.setLanguage(bookDetails.getLanguage());
            return bookRepository.save(book);
        }).orElseThrow(() -> new BookNotFoundException("Книга с id " + id + " не найдена"));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
