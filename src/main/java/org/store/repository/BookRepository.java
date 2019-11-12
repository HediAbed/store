package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByActiveTrue();
	Book findByIdInAndActiveTrue(Long id);
	List<Book> findByTitleContaining(String keyword);
}
