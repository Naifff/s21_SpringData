package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	private Book testBook;

	@BeforeEach
	void setUp() {
		testBook = new Book(1L, "Test Book", "Test Author", 2024);
	}

	@Test
	void getAllBooks_ShouldReturnAllBooks() {
		when(bookRepository.findAll()).thenReturn(List.of(testBook));

		List<Book> result = bookService.getAllBooks();

		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(testBook);
	}

	@Test
	void getBookById_WhenBookExists_ShouldReturnBook() {
		when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

		Optional<Book> result = bookService.getBookById(1L);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testBook);
	}

	@Test
	void createBook_ShouldSaveAndReturnBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(testBook);

		Book result = bookService.createBook(testBook);

		assertThat(result).isEqualTo(testBook);
		verify(bookRepository).save(testBook);
	}

	@Test
	void updateBook_WhenBookExists_ShouldUpdateAndReturnBook() {
		when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
		when(bookRepository.save(any(Book.class))).thenReturn(testBook);

		Optional<Book> result = bookService.updateBook(1L, testBook);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testBook);
		verify(bookRepository).save(testBook);
	}

	@Test
	void deleteBook_ShouldCallRepository() {
		bookService.deleteBook(1L);

		verify(bookRepository).deleteById(1L);
	}
}