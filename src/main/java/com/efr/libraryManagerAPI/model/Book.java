package com.efr.libraryManagerAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String createYear;
    private String language;
    private String genre;
    private BigDecimal price;
    private int pages;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    public Book(String title, String createYear, String language, String genre, BigDecimal price, int pages) {
        this.title = title;
        this.createYear = createYear;
        this.language = language;
        this.genre = genre;
        this.price = price;
        this.pages = pages;
    }
}
