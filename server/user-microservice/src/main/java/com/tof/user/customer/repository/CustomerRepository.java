package com.tof.user.customer.repository;

import com.tof.user.customer.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}
