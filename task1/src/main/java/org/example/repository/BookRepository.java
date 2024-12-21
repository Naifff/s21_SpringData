package org.example.repository;

import org.example.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	// Spring Data JDBC will automatically implement CRUD operations
	// We can add custom query methods if needed
}