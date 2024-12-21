package org.example.init;

import lombok.RequiredArgsConstructor;
import org.example.model.Book;
import org.example.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
	private final BookService bookService;

	@Override
	public void run(String... args) {
		bookService.createBook(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 1925));
		bookService.createBook(new Book(null, "To Kill a Mockingbird", "Harper Lee", 1960));
		bookService.createBook(new Book(null, "1984", "George Orwell", 1949));
	}
}