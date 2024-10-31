package com.efr.libraryManagerAPI.service.interfaces;

import com.efr.libraryManagerAPI.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookServiceInterface {

    Page<Book> getAllBooks(Pageable pageable);

    Optional<Book> getBookById(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book bookDetails);

    void deleteBook(Long id);
}
