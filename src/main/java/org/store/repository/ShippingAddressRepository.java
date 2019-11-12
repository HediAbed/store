package org.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
	
}
