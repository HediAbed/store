package org.store.service;


import org.store.domain.Book;
import org.store.domain.CartItem;
import org.store.domain.ShoppingCart;
import org.store.domain.User;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
	
	CartItem addBookToCartItem(Book book, User user, int qty);
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
//	List<CartItem> findByOrder(Order order);
	
	CartItem updateCartItem(CartItem cartItem);
	
	void removeCartItem(CartItem cartItem);

	Optional<CartItem> findById(Long id);
	
	CartItem save(CartItem cartItem);
}
