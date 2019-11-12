package org.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.Order;
import org.store.domain.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(User user);
}
