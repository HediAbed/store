package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.UserShipping;

public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {

}
