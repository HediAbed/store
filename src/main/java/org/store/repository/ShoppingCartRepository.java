package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
