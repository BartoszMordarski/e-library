package com.example.elibrary.controller;

import com.example.elibrary.controller.BookController;
import com.example.elibrary.model.Book;
import com.example.elibrary.model.dto.BookDto;
import com.example.elibrary.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setIsbn("1234567890");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setIsbn("0987654321");

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    @WithMockUser
    void testGetBookById() throws Exception {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setIsbn("1234567890");

        when(bookService.getBookById(id)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1234567890"));

        verify(bookService, times(1)).getBookById(id);
    }

    @Test
    @WithMockUser
    void testAddBook() throws Exception {
        // Mock data
        BookDto bookDto = new BookDto();
        bookDto.setTitle("New Book");
        bookDto.setAuthor("New Author");
        bookDto.setIsbn("9876543210");

        Book newBook = new Book();
        newBook.setId(1L);
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setIsbn("9876543210");

        // Mock behavior
        when(bookService.addBook(any(BookDto.class))).thenReturn(newBook);

        // Perform POST request
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("9876543210"));

        // Verify mock interactions
        verify(bookService, times(1)).addBook(any(BookDto.class));
    }

    @Test
    @WithMockUser
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBookById(1L);

        mockMvc.perform(delete("/books/{id}", 1L).with(csrf()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBookById(1L);
    }

    @Test
    @WithMockUser
    void testUpdateBook() throws Exception {
        Long id = 1L;

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Updated Book");
        bookDto.setAuthor("Updated Author");
        bookDto.setIsbn("1112223330");

        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("1112223330");

        when(bookService.updateBook(eq(id), any(BookDto.class))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Updated Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1112223330"));

        verify(bookService, times(1)).updateBook(eq(id), any(BookDto.class));
    }
}