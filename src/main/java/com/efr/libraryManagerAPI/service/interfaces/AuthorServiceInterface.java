package com.efr.libraryManagerAPI.service.interfaces;

import com.efr.libraryManagerAPI.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorServiceInterface {

    List<Author> getAllAuthors();

    Optional<Author> getAuthorById(Long id);

    Author createAuthor(Author author);

    Author updateAuthor(Long id, Author authorDetails);

    void deleteAuthor(Long id);
}
