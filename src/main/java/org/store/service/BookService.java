package org.store.service;



import org.store.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
	
	List<Book> findAll();

	List<Book> findByActiveTrue();

	Optional<Book> findOne(Long id);

	Optional<Book> findByIdActiveBook(Long id);

	Book save(Book book);
	
	List<Book> blurrySearch(String title);
	
	void removeOne(Long id);
}
