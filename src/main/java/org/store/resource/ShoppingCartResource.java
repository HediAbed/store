package org.store.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.store.domain.Book;
import org.store.domain.CartItem;
import org.store.domain.ShoppingCart;
import org.store.domain.User;
import org.store.dto.request.ItemToCartRequest;
import org.store.service.BookService;
import org.store.service.CartItemService;
import org.store.service.ShoppingCartService;
import org.store.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class ShoppingCartResource {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@PostMapping("/add")
	public ResponseEntity addItem (@RequestBody ItemToCartRequest item){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> user = userService.findByUsername(username);
		Optional<Book> book = bookService.findOne(item.getId());
		if (!book.isPresent())
			return new ResponseEntity("book not found!", HttpStatus.BAD_REQUEST);
		if(item.getQuantity() > book.get().getInStockNumber())
			return new ResponseEntity("Not Enough Stock!", HttpStatus.BAD_REQUEST);

		cartItemService.addBookToCartItem(book.get(), user.get(), item.getQuantity());
		return new ResponseEntity("Book Added Successfully!", HttpStatus.OK);
	}
	
	@GetMapping("/getCartItemList")
	public List<CartItem> getCartItemList() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		ShoppingCart shoppingCart = user.getShoppingCart();
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		shoppingCartService.updateShoppingCart(shoppingCart);
		return cartItemList;
	}
	
	@GetMapping("/getShoppingCart")
	public ShoppingCart getShoppingCart() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		return shoppingCart;
	}
	
	@DeleteMapping("/removeItem/{id}")
	public ResponseEntity removeItem(@PathVariable Long id) {
		Optional<CartItem> cartItem=cartItemService.findById(id);
		if (cartItem.isPresent())
			cartItemService.removeCartItem(cartItem.get());
		else
			return new ResponseEntity("CartItem Not Found!", HttpStatus.BAD_REQUEST);

		return new ResponseEntity("Cart Item Removed Successfully!", HttpStatus.OK);
	}
	
	@PutMapping("/updateCartItem")
	public ResponseEntity updateCartItem(@RequestBody ItemToCartRequest item){
		Optional<CartItem> cartItem = cartItemService.findById(item.getId());

		if (cartItem.isPresent())
			cartItem.get().setQty(item.getQuantity());
		else
			return new ResponseEntity("CartItem Not Found!", HttpStatus.BAD_REQUEST);

		cartItemService.updateCartItem(cartItem.get());
		
		return new ResponseEntity("Cart Item Updated Successfully!", HttpStatus.OK);
	}
	
}
