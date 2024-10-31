package com.efr.libraryManagerAPI.service;

import com.efr.libraryManagerAPI.exception.AuthorNotFoundException;
import com.efr.libraryManagerAPI.model.Author;
import com.efr.libraryManagerAPI.repository.AuthorRepository;
import com.efr.libraryManagerAPI.service.interfaces.AuthorServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements AuthorServiceInterface {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, Author authorDetails) {
        return authorRepository.findById(id).map(author -> {
            author.setName(authorDetails.getName());
            author.setGenre(authorDetails.getGenre());
            author.setSurname(authorDetails.getSurname());
            author.setLanguage(authorDetails.getLanguage());
            return authorRepository.save(author);
        }).orElseThrow(() -> new AuthorNotFoundException("Автор с id " + id + " не найден"));
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
