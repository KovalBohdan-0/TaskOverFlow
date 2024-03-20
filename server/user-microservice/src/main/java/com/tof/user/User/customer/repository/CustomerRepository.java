package com.tof.user.User.customer.repository;

import com.tof.user.User.customer.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Boolean> existsByEmail(String email);
    Mono<Customer> findByEmail(String email);
}
