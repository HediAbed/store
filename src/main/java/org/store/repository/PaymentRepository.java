package org.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.store.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
