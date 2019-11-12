package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.CartItem;
import org.store.domain.ShoppingCart;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
//	List<CartItem> findByOrder(Order order);
}
