package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Book;
import org.example.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = {BookController.class})
class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Autowired
	private ObjectMapper objectMapper;

	private Book testBook;

	@BeforeEach
	void setUp() {
		testBook = new Book(1L, "Test Book", "Test Author", 2024);
	}

	@Test
	void getAllBooks_ShouldReturnBooks() throws Exception {
		when(bookService.getAllBooks()).thenReturn(List.of(testBook));

		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(List.of(testBook))));
	}

	@Test
	void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

		mockMvc.perform(get("/api/books/1"))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(testBook)));
	}

	@Test
	void getBookById_WhenBookDoesNotExist_ShouldReturnNotFound() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/books/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createBook_ShouldReturnCreatedBook() throws Exception {
		when(bookService.createBook(any(Book.class))).thenReturn(testBook);

		mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testBook)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(testBook)));
	}

	@Test
	void updateBook_WhenBookExists_ShouldReturnUpdatedBook() throws Exception {
		when(bookService.updateBook(eq(1L), any(Book.class)))
				.thenReturn(Optional.of(testBook));

		mockMvc.perform(put("/api/books/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testBook)))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(testBook)));
	}

	@Test
	void deleteBook_ShouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/api/books/1"))
				.andExpect(status().isNoContent());
	}
}