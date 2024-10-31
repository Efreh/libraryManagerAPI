package com.efr.libraryManagerAPI.repository;

import com.efr.libraryManagerAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
