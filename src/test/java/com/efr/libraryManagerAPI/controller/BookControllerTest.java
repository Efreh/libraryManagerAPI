package com.efr.libraryManagerAPI.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.efr.libraryManagerAPI.exception.BookNotFoundException;
import com.efr.libraryManagerAPI.model.Book;
import com.efr.libraryManagerAPI.service.interfaces.BookServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceInterface bookService;

    @Autowired
    private BookController bookController;

    @Test
    void getAllBooks_ShouldReturnPaginatedBooks() throws Exception {
        Book book = new Book("Test Book", "2023", "English", "Fiction", new BigDecimal("9.99"), 300);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        when(bookService.getAllBooks(pageable)).thenReturn(bookPage);

        mockMvc.perform(get("/api/books")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"))
                .andExpect(jsonPath("$.content[0].language").value("English"))
                .andExpect(jsonPath("$.content[0].price").value(9.99))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        Book book = new Book("Test Book", "2023", "English", "Fiction", new BigDecimal("9.99"), 300);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.language").value("English"))
                .andExpect(jsonPath("$.price").value(9.99));
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book Not Found"))
                .andExpect(jsonPath("$.message").value("Книга с id 1 не найдена"));
    }

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        Book book = new Book("New Book", "2023", "English", "Fiction", new BigDecimal("19.99"), 250);
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\",\"createYear\":\"2023\",\"language\":\"English\",\"genre\":\"Fiction\",\"price\":19.99,\"pages\":250}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.price").value(19.99));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook_WhenBookExists() throws Exception {
        Book book = new Book("Updated Book", "2023", "English", "Non-Fiction", new BigDecimal("15.99"), 320);
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Book\",\"createYear\":\"2023\",\"language\":\"English\",\"genre\":\"Non-Fiction\",\"price\":15.99,\"pages\":320}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.genre").value("Non-Fiction"))
                .andExpect(jsonPath("$.price").value(15.99));
    }

    @Test
    void deleteBook_ShouldReturnNoContent_WhenBookExists() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        doThrow(new BookNotFoundException("Книга с id 1 не найдена")).when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book Not Found"))
                .andExpect(jsonPath("$.message").value("Книга с id 1 не найдена"));
    }
}
