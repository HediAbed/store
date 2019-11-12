package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.store.domain.BookToCartItem;
import org.store.domain.CartItem;

public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
