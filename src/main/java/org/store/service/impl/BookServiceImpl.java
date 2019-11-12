package org.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.domain.Book;
import org.store.repository.BookRepository;
import org.store.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	@Transactional
	@Override
	public List<Book> findByActiveTrue() {
		return bookRepository.findByActiveTrue();
	}

	@Override
	public Optional<Book> findOne(Long id) {
		return Optional.ofNullable(bookRepository.findOne(id));
	}

	@Transactional
	@Override
	public Optional<Book> findByIdActiveBook(Long id) {
		return Optional.ofNullable(bookRepository.findByIdInAndActiveTrue(id));
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}


	@Transactional
	@Override
	public List<Book> blurrySearch(String keyword) {
		return bookRepository.findByTitleContaining(keyword).stream()
				.filter(book -> book.isActive()).collect(Collectors.toList());
	}
	
	public void removeOne(Long id) {
		bookRepository.delete(id);
	}
}
