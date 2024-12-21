package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("BOOK")
public class Book {
	@Id
	private Long id;

	@Column("TITLE")
	private String title;

	@Column("AUTHOR")
	private String author;

	@Column("PUBLICATION_YEAR")
	private Integer publicationYear;
}