package org.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.BillingAddress;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {

}
