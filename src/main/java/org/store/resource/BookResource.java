package org.store.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.store.domain.Book;
import org.store.dto.BookDto;
import org.store.dto.request.CreateBookRequest;
import org.store.mapper.BookMapper;
import org.store.service.BookService;


import java.io.IOException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private BookMapper bookMapper;
	
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity addBookPost(@RequestBody CreateBookRequest book) {
		 bookService.save(bookMapper.toBook(book));
		return new ResponseEntity("New book Added Successfully!",HttpStatus.CREATED);
	}

	@PutMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity updateBookPost(@RequestBody Book book) {
		if (bookService.findOne(book.getId()).isPresent()){
			bookService.save(book);
			return new ResponseEntity("Update Success", HttpStatus.OK);
		}
		else
			return new ResponseEntity("Book not found!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Book>> getBookList() {
		return ResponseEntity.ok(bookService.findAll());
	}

	@GetMapping("/active")
	public ResponseEntity<List<BookDto>> getActiveBookList() {
		return ResponseEntity.ok(bookService.findByActiveTrue().stream().map(bookMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new)));
	}


	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity deleteBook(@PathVariable Long id)  throws IOException{
		bookService.removeOne(id);
		return new ResponseEntity("Remove Success!", HttpStatus.OK);
	}
	
	@GetMapping("/details/{id}")
	public ResponseEntity geBook(@PathVariable("id") Long id){
		Optional<Book> book = bookService.findByIdActiveBook(id);
		if (book.isPresent())
			return ResponseEntity.ok(bookMapper.toDto(book.get()));
		 else
			return ResponseEntity.badRequest().body("book not found");
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity getBook(@PathVariable("id") Long id){
		Optional<Book> book = bookService.findOne(id);
		if (book.isPresent())
			return ResponseEntity.ok(book.get());
		 else
			return ResponseEntity.badRequest().body("book not found");
	}
	
	@GetMapping(value="/search/{keyword}")
	public ResponseEntity<List<BookDto>>  searchBook (@PathVariable String keyword) {
		return ResponseEntity.ok(bookService.blurrySearch(keyword).stream().map(bookMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new)));
	}
}
