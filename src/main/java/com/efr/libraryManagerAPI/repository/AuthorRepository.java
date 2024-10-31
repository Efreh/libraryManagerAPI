package com.efr.libraryManagerAPI.repository;

import com.efr.libraryManagerAPI.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
